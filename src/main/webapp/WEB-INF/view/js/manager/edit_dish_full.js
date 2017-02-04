/**
 * Created by Andrey on 03.02.2017.
 */

$(document).ready(function () {
    var dishId = $.url().param("id");
    var url = "/admin/get_dish?dishId="+parseInt(dishId);
    $.ajax({
        type: 'POST',
        url: url,
        contentType: 'application/json;',
        dataType: 'json',
        success: displayDish,
        error: function(e) {
            display(e);
        }
    });
});

function displayDish(dish) {
    console.log("Dish: "+JSON.stringify(dish));
    $('#id').val(dish.id);
    $('#name').val(dish.name);
    $('#category').val(dish.category);
    $('#weight').val(dish.weight);
    $('#price').val(dish.price);
    $('#ingredients').val(dish.ingredients);
    $('#menus').val(dish.menus);
    var dishsIngredientsTable = $('#dishsIngredientsTable').DataTable({
        "bLengthChange": false,
        "ajax": {
            url: "/get_dishs_ingredients?dishId=" + dish.id,
            "type": "POST",
            "dataType": "json"
        },
        columns: [
            { "data": "id", "visible": false, "title": "ID", "sortable":false},
            { "data": "name", "name": "name", "title": "Ing."},
            { "data": "quantity", "title": "Quantity", "sortable":false, "render": function(data) {
                return '<input type="text" value = "' + data+ '" size = "2" name="input"/>'
            }
            },
            { "data": "unit", "name": "unit", "title": "Unit", "sortable":false},
            { "data": null, "sortable":false, "render": function() {
                return '<button type="button" class="btn btn-default" id="delRow" name ="delRow">Del</button>'
            }
            }
        ]
    });

    $('#dishsIngredientsTable tbody').on('click', 'button', function () {
        dishsIngredientsTable.row($(this).parents('tr')).remove().draw();
    });

}

$(document).ready(function () {
    var ingredientsTable = $('#ingredientsTable').DataTable({
        "ajax" : {
            "url": "/admin/get_all_ingredients",
            "type": "POST",
            "dataType": "json"
        },
        columns: [
            { "data": "id", "visible": true, "title": "ID"},
            { "data": "name", "name": "name", "title": "Ingredient"},
            { "data": "unit.name", "name": "unit", "title": "Unit"},

            { "data": null,"defaultContent": "<button>Add</button>"}
        ]
    });

    $('#ingredientsTable tbody').on('click', 'button', function () {
        var data = ingredientsTable.row($(this).parents('tr')).data();
        var t = $('#dishsIngredientsTable').DataTable();
        t.row.add({
            "id": data.id,
            "name": data.name,
            "quantity": 0.0,
            "unit": data.unit.name
        }).draw();
    });
});


$(document).ready(function () {
    $('#editIngredientsForm').submit(function (event) {
        var updatedDish = new Object();
        updatedDish.dishId = parseInt($.url().param("dishId"));
        updatedDish.name=$('#name').val();
        updatedDish.category=$('#category').val();
        updatedDish.weight=parseFloat($('#weight').val());
        updatedDish.price=parseFloat($('#price').val());
        updatedDish.ingredients = getIngredients();
        updatedDish.menus = $('#menus').val();
        $.ajax({
            url: "/admin/update_dishs_ingredients",
            dataType: 'json',
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(updatedDish),
            success: function (data) {
                location.href = '/admin/dishes';
            },
            error: function (e) {
                display(e);
            },
            done: function (e) {
                console.log("DONE");
            }
        });
        event.preventDefault();
    });

    function getIngredients() {
        var dishTable = $('#dishsIngredientsTable').DataTable();
        var ingredients = [];
        //<![CDATA[
        var ingredientIdArray = dishTable
            .columns(0)
            .data()
            .eq(0)
            .toArray();
        var quantityString = dishTable
            .columns(2)
            .data()
            .eq(0)
            .$('input')
            .serialize();
        var quantityArray = quantityString.split("&");
        var len = ingredientIdArray.length;
        for (var i = 0; i < len; i++) {
            ingredients.push({
                ingId: parseInt(ingredientIdArray[i]),
                quantity: parseFloat(quantityArray[i]
                    .slice(6))
            });
        }
        //]]>
        return ingredients;
    }
});


function display(data) {
    var json = "<h4>Error</h4><pre>"
        + data.responseText+ "</pre>";
    $('#feedback').html(json);
}

$(document).ready(function () {
    $("#myModal").draggable({
        handle: ".modal-header"
    })
});