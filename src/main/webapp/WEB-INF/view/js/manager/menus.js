/**
 * Created by Andrey on 07.02.2017.
 */
$(document).ready(function () {
    var table = $('#menu').DataTable({
        "bFilter":true,
        "bLengthChange": false,
        "bAutoWidth": false,
        "ajax" : {
            "url": "/get_menus",
            "type": "POST"
        },
        "columnDefs": [
            { "width": "1%", "targets": 0 },
            { "width": "39%", "targets": 1 },
            { "width": "15%", "targets": 2 },
            { "width": "15%", "targets": 3 },
            { "width": "10%", "targets": 4 },
            { "width": "10%", "targets": 5 },
            { "width": "10%", "targets": 6 }
        ],
        columns: [
            { "data": "id", "name": "id",  "title": "id", "visible": true},
            { "data": "name", "name": "name", "title": "Menu"},
            { "data": "dishes", "name": "dishes", "title": "Dishes, #"},
            { "data": "price", "name": "price", "title": "Price, $"},


            { "data": null, "sortable": false, "render": function(data){
                return '<a href="/admin/edit_menu?menuId=' + data.id + '"><input type="button" class="btn btn-default" value="Edit"/></a>';
            }
            },
            { "data": null, "sortable": false, "render": function(data){
                return '<a href="/admin/edit_menus_dishes?menuId=' + data.id + '"><input type="button" class="btn btn-default" value="Edit Dishes"/></a>';
            }
            },
            { "data": null, "sortable": false, "render": function(data){
                return '<a href="/admin/delete_menu?menuId=' + data.id + '"><input type="button" class="btn btn-default" value="Del"/></a>';
            }
            }
        ]
    });
});

$(document).ready(function() {
    // Setup - add a text input to each footer cell
    $('#menu thead th.search').each( function () {
        var title = $(this).text();
        $(this).html( '<input type="text" size="15" placeholder="'+title+'" />' );
    } );

    // DataTable
    var table = $('#menu').DataTable();

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


