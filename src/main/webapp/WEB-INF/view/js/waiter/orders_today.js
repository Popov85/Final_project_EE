$(document).ready(function () {

    // Set up today's date in header
    $('#date').text(moment().format('MMM Do YY'));

    var table = $('#ordsTable').DataTable({
        "ajax" : {
            "url": "/waiter/get_orders?waiterId="+parseInt($('#waiterId').val()),
            "type": "POST"
        },
        serverSide: false,
        sSortDataType: "dom-checkbox",
        order: [[ 1, "desc" ]],
        columns: [
            { "data": "id", "name": "id",  "title": "#", "visible": true},
            { "data": "opened", "name": "isOpened", "title": "isOpened", "visible":false},
            { "data": null, "name": "openedTimeStamp", "title": "opened time", "render": function(data){
                return moment(data.openedTimeStamp).format('HH:mm');
            }
            },
            { "data": null, "name": "closedTimeStamp", "title": "closed time", "render": function(data){
                if (data.closedTimeStamp==null) {
                    return "-";
                } else {
                    return moment(data.closedTimeStamp).format('HH:mm');
                }
            }
            },
            { "data": "table", "name": "table", "title": "table"},
            { "data": "dishesQuantity", "title": "dishes", "sortable": false},
            { "data": "totalSum", "title": "total, $", "sortable": false},
            { "data": "readiness", "title": "readiness, %", "sortable": true},
            { "data": "fulfilled", "name": "fulfilled", "title": "isFulfilled", "visible": false},
            { "data": null, "name": "cancelled", "title": "isCancelled" , "render": function(data){
                if (data.cancelled) {
                    return '<p hidden="true">true</p><input type="checkbox" disabled="true" checked/>';
                } else {
                    return '<p hidden="true">false</p><input type="checkbox" disabled="true"/>';
                }
            }},
            { "data": null, "sortable": false, "render": function(data){
                if (data.cancelled || !data.opened || data.readiness!="0.0 %") {
                    return '<input type="button" class="btn btn-default" disabled="true" title="Operation is forbidden! &#013; Partially fulfilled orders cannot be edited in this version of software!" value="Edit"/>';
                } else {
                    var param = 'orderId='+data.id;
                    return '<a href="javascript:checkOrderStatus(\'' + param + '\',\'' + 'editOrder' + '\')">' +
                                '<input type="button" class="btn btn-default" value="Edit"/>' +
                            '</a>';
                }
            }
            },
            { "data": null, "sortable": false, "render": function(data){
                if (data.cancelled || !data.opened) {
                    return '<input type="button" class="btn btn-default" disabled="true" title="Operation is forbidden!" value="Cancel"/>';
                } else {
                    return '<a href="javascript:cancelOrder(\'' + data.id+ '\')">' +
                                '<input type="button" class="btn btn-default" value="Cancel"/>' +
                            '</a>';
                }
            }
            },
            { "data": null, "sortable": false, "render": function(data){
                if (data.cancelled || !data.opened) {
                    return '<input type="button" class="btn btn-default" disabled="true" title="Operation is forbidden!" value="Close"/>';
                } else {
                    var param = 'orderId='+data.id;
                    return '<a href="javascript:checkOrderStatus(\'' + param + '\',\'' + 'closeOrder' + '\')">' +
                                '<input type="button" class="btn btn-default" value="Close"/>' +
                            '</a>';
                }
            }
            }
        ]
    });
});

function cancelOrder(param) {
    var url = "/waiter/cancel_order?id="+param;
    $.ajax({
        type: 'GET',
        url: url,
        contentType: 'application/json',
        dataType: 'json',
        success: function(data){
            reloadOrdersTable();
            sendMessage(data);
        },
        error: function(xhr, textStatus, errorThrown) {
            // TODO redirect to error page
            console.log('Error cancelling Order');
            window.location.replace("/error");
        }
    });
}

function sendMessage(data) {
    var stompClient = null;
    var socket = new SockJS('/messaging/waiter');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.send("/app/messaging/waiter", {}, JSON.stringify(
            {'time': 'set on server', "action": "cancelled", 'order': ' Order# '+data.orderId}
            )
        );
        stompClient.disconnect();
    });
}

function checkOrderStatus(param, callBackFunction) {
    var url = "/waiter/check_order?"+param;
    $.ajax({
        type: 'POST',
        url: url,
        contentType: 'application/json',
        dataType: 'json',
        success: window[callBackFunction], // closeOrder Or editOrder
        error: function(xhr, textStatus, errorThrown) {
            // TODO redirect to error page with params
            console.log('Error checking Order status');
            window.location.replace("/error");
        }
    });
}

function closeOrder(data) {
    var param = 'id=' + data.orderId;
    console.log(data.hasPrepared);
    var url = "/waiter/close_order?"+param;
    if (!data.isFulfilled) {
        alert("This Order is not fulfilled yet!");
    } else if (data.isCancelled) {
        alert("Cancelled Orders cannot be closed!");
    } else {
        // AJAX to close Order and reload table ONLY!
        $.ajax({
            type: 'GET',
            url: url,
            success: reloadOrdersTable,
            error: function(xhr, textStatus, errorThrown) {
                // TODO redirect to error page with params
                console.log('Error closing Order');
                window.location.replace("/error");
            }
        });
    }
}

function editOrder(data) {
    var param = 'id=' + data.orderId;
    var url = "/waiter/edit_order?"+param;
    if (data.hasPrepared) {
        alert("This Order is already partially fulfilled! Further editing is limited in this version of software!");
        // Reload only table
        reloadOrdersTable();
    } else {
        // TODO launch modal, set orderId, get Order's dishes and specific info.
        window.location.replace(url);
    }
}

function reloadOrdersTable() {
    $('#ordsTable').DataTable()
        .ajax.url(
        "/waiter/get_orders?waiterId="+parseInt($('#waiterId').val())
    ).load()
}
