$(document).ready(function () {
    var table = $('#ordsTable').DataTable({
        "ajax" : {
            "url": "/waiter/get_orders?waiterId=2",
            "type": "POST"
        },
        serverSide: false,
        columns: [
            { "data": "id", "name": "id",  "title": "id", "visible": true},
            { "data": "opened", "name": "isOpened", "title": "isOpened"},
            { "data": "openedTimeStamp", "name": "openedTimeStamp", "title": "opened time"},
            { "data": "closedTimeStamp", "name": "closedTimeStamp", "title": "closedTimeStamp"},
            { "data": "table", "name": "table", "title": "table"},
            { "data": "dishesQuantity", "title": "dishes", "sortable": false},
            { "data": "total", "title": "total", "sortable": false},

            { "data": null, "sortable": false, "render": function(data){
                return '<a href="/edit_order?id=' + data.id + '"><input type="button" class="btn btn-default" value="Edit"/></a>';
            }
            },

            { "data": null, "sortable": false, "render": function(data){
                return '<a href="/delete_order?id=' + data.id + '"><input type="button" class="btn btn-default" value="Delete"/></a>';
            }
            },

            { "data": null, "sortable": false, "render": function(data){
                return '<a href="/close_order?id=' + data.id + '"><input type="button" class="btn btn-default" value="Close"/></a>';
            }
            }
        ]
    });
});
