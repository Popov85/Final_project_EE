/**
 * Created by Andrey on 03.02.2017.
 */

$(document).ready(function () {
    var dishId = $.url().param("dishId");
    $.ajax({
        url: "/get_dishs_ingredients?dishId="+dishId,
        type: "POST",
        dataType: "json",
        success: getCurrentDishsIngredients,
        error: function(data) {
            console.log('Error getting ingredients of the dish #: '+dishId);
            display(data);
        }
    });
});

    // Set up Dish's ingredients (if present)
/*
    console.log("currentDish= "+JSON.stringify(currentDish));
    console.log("currentDish.ingredients= "+JSON.stringify(currentDish.ingredients));
    var table = $('#dishTable').DataTable({
        bLengthChange: false,
        "ajax" : {
            //"data": {"orderId": $("#id").val()},
            "url": "/get_dishs_ingredients?"+currentDish.id,
            "type": "POST",
            "dataType": "json"
        },
        columns: [
            { "data": "id", "visible": false, "title": "ID"},
            { "data": "name", "name": "name", "title": "Dish"},
            { "data": "quantity", "title": "Quantity", "render": function(data) {
                return '<input type="text" value = "' + data+ '" size = "2" name="input"/>'
            }
            },
            { "data": "unit", "name": "unit", "title": "Unit"},
            { "data": null, "render": function() {
                return '<button type="button" class="btn btn-default" id="delRow" name ="delRow">Del</button>'
            }
            }
        ]
    });
    // Remove a selected dish from order
    $('#dishTable tbody').on('click', 'button', function () {
        table.row($(this).parents('tr')).remove().draw();
    });



// Set up an Ingredient table in modal window
$(document).ready(function () {
    var table = $('#ingTable').DataTable({
        "ajax" : {
            "url": "/get_ingredients?"+currentDish.id,
            "type": "POST",
            "dataType": "json"
        },
        columns: [
            { "data": "id", "visible": true, "searchable": false},
            { "data": "name", "name": "name", "title": "Ingredient"},
            { "data": "unit.name", "name": "unit", "title": "Unit"},

            { "data": null,"defaultContent": "<button>Add</button>"}
        ]
    });

    //Adds a selected dish to the order
    $('#dishTable tbody').on('click', 'button', function () {
        var data = table.row($(this).parents('tr')).data();
        var t = $('#dishTable').DataTable();
        t.row.add({
            "id": data.id,
            "name": data.name,
            "quantity": 0.0,
            "unit": data.unit
        }).draw();
    });
});

$(document).ready(function () {
    $('#newOrderForm').submit(function (event) {
        var updatedDish = new Object();
        updatedDish.id = currentDish.id;
        updatedDish.dishes = getIngredients();
        $.ajax({
            url: "/update_dish",
            dataType: 'json',
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(updatedDish),
            success: function (data) {
                console.log("Success");
                location.href='/admin/dishes';
            },
            error: function (e) {
                console.log("ERROR: ", e);
                display(e);
            },
            done: function (e) {
                console.log("DONE");
            }
        });
        event.preventDefault();
    });

    // Prepares a dish array object as a part of data to be sent to the server
    function getIngredients() {
        var dishTable = $('#dishTable').DataTable();
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
                ingId: ingredientIdArray[i],
                quantity: parseInt(quantityArray[i]
                    .slice(6))
            });
        }
        //]]>
        return ingredients;
    }

    // Displays the server's feedback on the page
    function display(data) {
        var json = "<h4>Error</h4><pre>"
            + data.responseText+ "</pre>";
        $('#feedback').html(json);
    }
});

// Makes the modal window draggable
$(document).ready(function () {
    $("#myModal").draggable({
        handle: ".modal-header"
    })
});
*/
function getCurrentDishsIngredients(ingredients) {

    var dishsIngredientsTable = $('#dishsIngredientsTable').DataTable({
        data: ingredients,
        columns: [
            { "data": "id", "visible": false, "title": "ID"},
            { "data": "name", "name": "name", "title": "Dish"},
            { "data": "quantity", "title": "Quantity", "render": function(data) {
                return '<input type="text" value = "' + data+ '" size = "2" name="input"/>'
            }
            },
            { "data": "unit", "name": "unit", "title": "Unit"},
            { "data": null, "render": function() {
                return '<button type="button" class="btn btn-default" id="delRow" name ="delRow">Del</button>'
            }
            }
        ]
    });

    $('#dishsIngredientsTable tbody').on('click', 'button', function () {
        dishsIngredientsTable.row($(this).parents('tr')).remove().draw();
    });

    //----------------------

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

    //Adds a selected dish to the order
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
}

function display(data) {
    var json = "<h4>Error</h4><pre>"
        + data.responseText+ "</pre>";
    $('#feedback').html(json);
}