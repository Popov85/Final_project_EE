$(document).ready(function () {
    var table = $('#ordsTable').DataTable({
        "ajax" : {
            "url": "/waiter/get_orders?waiterId="+parseInt($('#waiterId').val()),
            "type": "POST"
        },
        serverSide: false,
        columns: [
            { "data": "id", "name": "id",  "title": "id", "visible": true},
            { "data": "opened", "name": "isOpened", "title": "isOpened"},
            { "data": null, "name": "openedTimeStamp", "title": "opened time", "render": function(data){
                var time = new Date(data.openedTimeStamp);
                return time.toLocaleTimeString();
            }
            },
            { "data": "closedTimeStamp", "name": "closedTimeStamp", "title": "closed time"},
            { "data": "table", "name": "table", "title": "table"},
            { "data": "dishesQuantity", "title": "dishes", "sortable": false},
            { "data": "totalSum", "title": "total", "sortable": false},
            { "data": "fulfilled", "name": "fulfilled", "title": "isFulfilled"},
            { "data": "cancelled", "name": "cancelled", "title": "isCancelled"},

            { "data": null, "sortable": false, "render": function(data){
                return '<a href="/edit_order?id=' + data.id + '"><input type="button" class="btn btn-default" value="Edit"/></a>';
            }
            },

            { "data": null, "sortable": false, "render": function(data){
                return '<a href="/cancel_order?id=' + data.id + '"><input type="button" class="btn btn-default" value="Cancel"/></a>';
            }
            },

            { "data": null, "sortable": false, "render": function(data){
                if (!data.fulfilled) {
                    return '<input type="button" class="btn btn-default" disabled="true" value="Close"/>';
                } else {
                    return '<a href="/close_order?id=' + data.id + '"><input type="button" class="btn btn-default" value="Close"/></a>';
                }

            }
            }
        ]
    });
});
