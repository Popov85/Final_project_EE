$(document).ready(function () {
    var table = $('#ordsTable').DataTable({
        "ajax" : {
            "url": "/waiter/get_orders?waiterId="+parseInt($('#waiterId').val()),
            "type": "POST"
        },
        serverSide: false,
        sSortDataType: "dom-checkbox",
        columns: [
            { "data": "id", "name": "id",  "title": "id", "visible": true},
            { "data": "opened", "name": "isOpened", "title": "isOpened", "visible":false},
            { "data": null, "name": "openedTimeStamp", "title": "opened time", "render": function(data){
                var time = new Date(data.openedTimeStamp);
                return time.toLocaleTimeString();
            }
            },
            { "data": null, "name": "closedTimeStamp", "title": "closed time", "render": function(data){
                if (data.closedTimeStamp==null) {
                    return "-";
                } else {
                    var time = new Date(data.closedTimeStamp);
                    return time.toLocaleTimeString();
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
                    return '<a href="/edit_order?id=' + data.id + '"><input type="button" class="btn btn-default" value="Edit"/></a>';
                }
            }
            },

            { "data": null, "sortable": false, "render": function(data){
                if (data.cancelled || !data.opened) {
                    return '<input type="button" class="btn btn-default" disabled="true" title="Operation is forbidden!" value="Cancel"/>';
                } else {
                    return '<a href="/cancel_order?id=' + data.id + '"><input type="button" class="btn btn-default" value="Cancel"/></a>';
                }
            }
            },

            { "data": null, "sortable": false, "render": function(data){
                if (!data.fulfilled || data.cancelled || !data.opened) {
                    return '<input type="button" class="btn btn-default" disabled="true" title="Operation is forbidden!" value="Close"/>';
                } else {
                    return '<a href="/close_order?id=' + data.id + '"><input type="button" class="btn btn-default" value="Close"/></a>';
                }

            }
            }
        ]
    });
});
