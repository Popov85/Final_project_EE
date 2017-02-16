/**
 * Created by Andrey on 07.02.2017.
 */

$(document).ready(function () {
    var menuId = $.url().param("menuId");
    var menusDishesTable = $('#menusDishesTable').DataTable({
        "bLengthChange": false,
        "ajax": {
            "url": "/all/get_menus_dishes?id=" + parseInt(menuId),
            "type": "POST",
            "dataType": "json"
        },
        columns: [
            { "data": "id", "visible": false, "title": "#", "sortable":false},
            { "data": "name", "name": "name", "title": "Dish"},
            { "data": "category", "name": "category", "title": "Category"},
            { "data": "weight", "name": "weight", "title": "Weight, g"},
            { "data": "price", "name": "price", "title": "Price, $"},

            { "data": null, "sortable":false, "render": function() {
                return '<button type="button" class="btn btn-default" id="delRow" name ="delRow">Del</button>'
            }
            }
        ]
    });

    $('#menusDishesTable tbody').on('click', 'button', function () {
        menusDishesTable.row($(this).parents('tr')).remove().draw();
    });

});

$(document).ready(function () {
    var dishesTable = $('#dishesTable').DataTable({
        "ajax" : {
            "url": "/all/get_all_dishes",
            "type": "POST"
        },
        serverSide: true,
        columns: [
            { "data": "id", "name": "id",  "title": "id", "visible": true},
            { "data": "name", "name": "name", "title": "Dish"},
            { "data": "category", "name": "category", "title": "Category"},
            { "data": "weight", "name": "weight", "title": "Weight, g"},
            { "data": "price", "name": "price", "title": "Price, $"},

            { "data": null, "defaultContent": "<button>Add</button>"}
        ]
    });

    //Adds a selected dish to the order
    $('#dishesTable tbody').on('click', 'button', function () {
        var data = dishesTable.row($(this).parents('tr')).data();
        var t = $('#menusDishesTable').DataTable();
        t.row.add({
            "id": data.id,
            "name": data.name,
            "category": data.category,
            "weight": data.weight,
            "price": data.price
        }).draw();
    });
});


$(document).ready(function () {
    $('#editDishesForm').submit(function (event) {
        var updatedMenu = new Object();
        updatedMenu.dishes = getDishes();
        $.ajax({
            url: "/admin/update_menus_dishes?menuId="+$.url().param("menuId"),
            dataType: 'json',
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(updatedMenu),
            success: function (data) {
                location.href = '/admin/menus';
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

    function getDishes() {
        var menusDishesTable = $('#menusDishesTable').DataTable();
        var dishes = [];
        //<![CDATA[
        var dishIdArray = menusDishesTable
            .columns(0)
            .data()
            .eq(0)
            .toArray();
        var len = dishIdArray.length;
        for (var i = 0; i < len; i++) {
            dishes.push({
                dishId: parseInt(dishIdArray[i])
            });
        }
        //]]>
        return dishes;
    }
});

// Makes the modal window draggable
$(document).ready(function () {
    $("#myModal").draggable({
        handle: ".modal-header"
    })
});


function display(data) {
    var json = "<h4>Error</h4><pre>"
        + data.responseText+ "</pre>";
    $('#feedback').html(json);
}
