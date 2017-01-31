/**
 * Created by Andrey on 31.01.2017.
 */
$(document).ready(function () {
    var table = $('#stock').DataTable({
        "ajax" : {
            "url": "/adm/get_stock_state",
            "type": "POST"
        },
        serverSide: true,
        columns: [
            { "data": "ingredient.id", "name": "ingredient",  "title": "id", "visible": true},
            { "data": "ingredient.name", "name": "ingredient", "title": "ingredient", "visible": true},
            { "data": null, "name": "quantity", "title": "quantity", "render": function(data) {
                return Number((data.quantity).toFixed(1));
            }},
            { "data": "ingredient.unit.name", "name": "ingredient", "title": "unit", "visible": true},

            { "data": null, "sortable": false, "render": function(data){
                return '<a href="/refill_ingredient?id=' + data.ingredient.id + '"><input type="button" class="btn btn-default" value="Refill"/></a>';
            }
            }
        ]
    });
});

$(document).ready(function() {
    // Setup - add a text input to each footer cell
    $('#stock thead tr th.select-filter').each( function () {
        var title = $(this).text();
        $(this).html( '<input type="text" size="15" placeholder="Ingredient" />' );
    } );

    var table = $('#stock').DataTable();
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
