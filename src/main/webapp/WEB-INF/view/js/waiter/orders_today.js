$(document).ready(function () {
    var table = $('#ordsTable').DataTable({
        "ajax" : {
            "url": "/waiter/get_orders?waiterId="+parseInt($('#waiterId').val()),
            "type": "POST"
        },
        serverSide: false,
        sSortDataType: "dom-checkbox",
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
                    return '<a href="/waiter/cancel_order?id=' + data.id + '"><input type="button" class="btn btn-default" value="Cancel"/></a>';
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

function checkOrderStatus(param, callBackFunction) {
    var url = "/waiter/check_order?"+param;
    $.ajax({
        type: 'POST',
        url: url,
        contentType: 'application/json;',
        dataType: 'json',
        success: window[callBackFunction],
        error: function(xhr, textStatus, errorThrown) {
            console.log('error');
        }
    });
}

function editOrder(data) {
    var param = 'id=' + data.orderId;
    var url = "/waiter/edit_order?"+param;
    if (data.hasPrepared) {
        alert("This Order is already partially fulfilled! Further editing is limited in this version of software!");
        location.reload(true);
    } else {
        window.location.replace(url);
    }
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
        window.location.replace(url);
    }
}
