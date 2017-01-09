/**
 * Created by Andrey on 1/9/2017.
 */
$(document).ready(function () {
    var table = $('#dish').DataTable({
        "bFilter":true,
        "bLengthChange": false,
        "bAutoWidth": false,
        "ajax" : {
            "url": "/get_all_dishes",
            "type": "POST"
        },
        "columnDefs": [
            { "width": "5%", "targets": 0 },
            { "width": "50%", "targets": 1 },
            { "width": "15%", "targets": 2 },
            { "width": "10%", "targets": 3 },
            { "width": "10%", "targets": 4 },
            { "width": "10%", "targets": 5 }
        ],
        columns: [
            { "data": "id", "name": "id",  "title": "id", "visible": true},
            { "data": "name", "name": "name", "title": "dish"},
            { "data": "category", "name": "category", "title": "category"},
            { "data": "weight", "name": "weight, g", "title": "weight, g"},
            { "data": "price", "name": "price, $", "title": "price, $"},
            { "data": "menus", "name": "menus", "title": "menus"},
            { "data": null, "sortable": false, "render": function(data){
                return '<a href="/show_ingredients?id=' + data.id + '"><input type="button" class="btn btn-default" value="Show"/></a>';
            }
            }
        ]
    });
});

$(document).ready(function() {
    // Setup - add a text input to each footer cell
    $('#dish tfoot th.search').each( function () {
        var title = $(this).text();
        $(this).html( '<input type="text" size="10" placeholder="Search '+title+'" />' );
    } );

    // DataTable
    var table = $('#dish').DataTable();

    // Apply the search
    table.columns().every( function () {
        var that = this;

        $( 'input', this.footer() ).on( 'keyup change', function () {
            if ( that.search() !== this.value ) {
                that
                    .search( this.value )
                    .draw();
            }
        } );
    } );
} );
