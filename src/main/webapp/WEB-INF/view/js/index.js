/**
 * Created by Andrey on 1/9/2017.
 */
$(document).ready(function () {
    var table = $('#dish').DataTable({
        "bFilter":true,
        "bLengthChange": false,
        "bAutoWidth": false,
        "ajax" : {
            "url": "/all/get_all_dishes",
            "type": "POST"
        },
        serverSide:true,
        "columnDefs": [
            { "width": "0%", "targets": 0 },
            { "width": "25%", "targets": 1 },
            { "width": "25%", "targets": 2 },
            { "width": "10%", "targets": 3 },
            { "width": "10%", "targets": 4 },
            { "width": "20%", "targets": 5 },
            { "width": "10%", "targets": 6 }
        ],
        columns: [
            { "data": "id", "name": "id",  "title": "id", "visible": false},
            { "data": "name", "name": "name", "title": "Dish"},
            { "data": "category", "name": "category", "title": "Category"},
            { "data": "weight", "name": "weight", "title": "Weight(g)"},
            { "data": "price", "name": "price", "title": "Price($)"},
            { "data": "menus", "name": "menus", "title": "Menus", "sortable": false},
            { "data": null, "title": "Details", "sortable": false, "render": function(data){
                return '<a href="/all/show_ingredients?id=' + data.id + '"><input type="button" class="btn btn-default" value="Show"/></a>';
            }
            }
        ]
    });
});

$(document).ready(function() {
    // Setup - add a text input to each footer cell
    $('#dish thead th.search').each( function () {
        var title = $(this).text();
        $(this).html( '<input type="text" size="12" placeholder="'+title+'" />' );
    } );

    // DataTable
    var table = $('#dish').DataTable();

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
