// Set up tables
$(document).ready(function () {
    $.ajax({
        type: "POST",
        data: null,
        url: '/get_tables',
        dataType: 'json',
        success: function(json) {
            var $el = $("#table");
            $el.empty();
            $.each(json, function(value, key) {
                $el.append($("<option></option>")
                    .attr("value", value).text(key));
            });
        }
    });

    // Make selected the Order's table
    $.ajax({
        type: "POST",
        data: {"orderId": $("#id").val()},
        url: '/get_orders_table',
        dataType: 'json',
        success: function(json) {
            console.log("table= "+json);
            var $el = $("#table");
            $("#table").val(json);
        }
    });
});

// Set up Order's dishes
$(document).ready(function () {
    console.log("id= "+$("#id").val());
    var table = $('#odTable').DataTable({
        bLengthChange: false,
        "ajax" : {
            "data": {"orderId": $("#id").val()},
            "url": "/get_orders_dishes",
            "type": "POST",
            "dataType": "json"
        },
        columns: [
            { "data": "id", "visible": false, "searchable": false},
            { "data": "name"},
            { "data": "quantity", "render": function(data) {
                return '<input type="text" value = "' + data+ '" onchange="alert(\'' + table.row($(this).parents('tr')) + '\')"size = "2" name="input"/>'
            }
            },
            { "data": "price"},
            { "data": null, "render": function() {
                return '<button type="button" class="btn btn-default" id="delRow" name ="delRow">Del</button>'
            }
            }
        ]
    });
    // Remove a selected dish from order
    $('#odTable tbody').on('click', 'button', function () {
        console.log("Deleted!");
        table.row($(this).parents('tr')).remove().draw();
    });
});


// Set up a Dish table in modal window
$(document).ready(function () {
    var table = $('#dTable').DataTable({
        "ajax" : {
            "url": "/get_dishes",
            "type": "POST",
            "dataType": "json"
        },
        columns: [
            { "data": "id", "visible": false, "searchable": false},
            { "data": "name"},
            { "data": "category"},
            { "data": "price"},
            { "data": "weight"},
            { "data": null,"defaultContent": "<button>Add</button>"}
        ]
    });

    //Adds a selected dish to the order
    $('#dTable tbody').on('click', 'button', function () {
        var data = table.row($(this).parents('tr')).data();
        var t = $('#odTable').DataTable();
        t.row.add({
            "id": data.id,
            "name": data.name,
            "quantity": 1,
            "price": data.price
        }).draw();
    });
});

// Submits data to the server
$(document).ready(function () {
    $('#newOrderForm').submit(function (event) {
        var json = new Object();
        json.id = parseInt($("#id").val());
        json.isOpened = true;
        json.openedTimeStamp = new Date();
        json.closedTimeStamp = null;
        json.table = $('#table').val();
        json.waiter = parseInt($('#waiterId').val()); // 2; Mr . Black
        json.dishes = getDishes();
        $.ajax({
            url: $("#newOrderForm").attr( "action"),
            dataType: 'json',
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(json),
            success: function (data) {
                console.log("Success");
                location.href='/waiter/orders/today';
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
    function getDishes() {
        var odTable = $('#odTable').DataTable();
        var dishes = [];
        //<![CDATA[
        var dishIdArray = odTable
            .columns(0)
            .data()
            .eq(0)
            .toArray();
        var quantityString = odTable
            .columns(2)
            .data()
            .eq(0)
            .$('input')
            .serialize();
        var quantityArray = quantityString.split("&");
        var len = dishIdArray.length;
        for (var i = 0; i < len; i++) {
            dishes.push({
                dishId: dishIdArray[i],
                quantity: parseInt(quantityArray[i]
                    .slice(6))
            });
        }
        //]]>
        return dishes;
    }

    // Displays the server's feedback on the page
    function display(data) {
        var json = "<h4>Error</h4><pre>"//+JSON.stringify(data, null, 4)
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