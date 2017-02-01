/**
 * Created by Andrey on 31.01.2017.
 */
$(document).ready(function () {
    var table = $('#stock').DataTable({
        "ajax" : {
            "url": "/admin/get_stock_state",
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
                var ing = new Object();
                    ing.id=data.ingredient.id;
                    ing.name=data.ingredient.name;
                    ing.unit=data.ingredient.unit.name;
                console.log("data: "+JSON.stringify(data, null, 4));
                return '<a href="javascript:openModal(\'' + ing.id +'\',\'' + ing.name +'\',\''+ing.unit+ '\')">' +
                            '<input type="button" class="btn btn-default" value="Refill"/>' +
                        '</a>';
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

$(document).ready(function () {
    $("#myModal").draggable({
        handle: ".modal-header"
    })
});

function openModal(id, name, unit) {
    $('#title').text(name);
    $('#id').val(id);
    $('#unit').text(unit);
    $("#myModal").modal();
}

function submitIncrease() {
    var url = "/admin/update_stock?ingId="+$('#id').val()+"&quantity="+$('#quantity').val();
    window.location.href = url;
}