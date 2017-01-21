$(document).ready(function () {
    var table = $('#ordsTable').DataTable({
        "ajax" : {
            "url": "/waiter/get_archive?waiterId="+parseInt($('#waiterId').val()),
            "type": "POST"
        },
        serverSide: true,
        columns: [
            { "data": "id", "name": "id",  "title": "id", "visible": true},
            { "data": "opened", "name": "isOpened", "title": "isOpened"},
            { "data": "openedTimeStamp", "name": "openedTimeStamp", "title": "opened time"},
            { "data": "closedTimeStamp", "name": "closedTimeStamp", "title": "closedTimeStamp"},
            { "data": "table", "name": "table", "title": "table"},
            { "data": "dishesQuantity", "title": "dishes", "sortable": false},
            { "data": "totalSum", "title": "total", "sortable": false},

            { "data": null, "sortable": false, "render": function(data){
                return '<a href="/edit_order?id=' + data.id + '"><input type="button" class="btn btn-default" value="Edit"/></a>';
            }
            },

            { "data": null, "sortable": false, "render": function(data){
                return '<a href="/delete_order?id=' + data.id + '"><input type="button" class="btn btn-default" value="Del"/></a>';
            }
            },

            { "data": null, "sortable": false, "render": function(data){
                return '<a href="/close_order?id=' + data.id + '"><input type="button" class="btn btn-default" value="Close"/></a>';
            }
            }
        ]
    });
});

$(document).ready(function() {
    // Setup - add a text input to each footer cell
    $('#ordsTable thead tr th.select-filter').each( function () {
        var title = $(this).text();
        $(this).html( '<input type="text" size="10" placeholder="Search '+title+'" />' );
    } );
    // DataTable
    var table = $('#ordsTable').DataTable();
    // Apply the search
    table.columns().every( function () {
        var that = this;

        $( 'input', this.header() ).on( 'keyup change', function () {
            if ( that.search() !== this.value ) {
                that
                    .search( this.value )
                    .draw();
            }
        } );
    } );
} );