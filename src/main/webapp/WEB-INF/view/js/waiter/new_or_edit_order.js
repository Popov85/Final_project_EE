/**
 * Created by Andrey on 02.03.2017.
 */
// Set up an empty Order table
$(document).ready(function () {
    var t = $('#odTable').DataTable({
        bLengthChange: false,
        columnDefs: [
            { "targets": 0, "data": "id", "name": "id", "visible": false, "searchable": false},
            { "targets": 1, "data": "name", "name": "name", "visible": true, "searchable": true},
            { "targets": 2, "data": "quantity", "name": "quantity", "visible": true, "searchable": true, "render": function(data) {
                return '<input type="text" value = "' + data+ '" size = "2" name="input"/>'
            }},
            { "targets": 3, "data": "price", "name": "price", "visible": true, "searchable": true},
            { "targets": 4, "data": null, "name": "quantity", "visible": true, "searchable": true, "defaultContent": '<button type="button" class="btn btn-default" id="delRow" name ="delRow">Del</button>'},
        ]
    });

    // Remove a selected dish from order
    $('#odTable tbody').on('click', 'button', function () {
        t.row($(this).parents('tr')).remove().draw();
    });
});

// Set up tables
$(document).ready(function () {
    $.ajax({
        type: "POST",
        data: null,
        url: '/waiter/get_tables',
        dataType: 'json',
        success: function (json) {
            var $el = $("#table");
            $el.empty();
            $.each(json, function (value, key) {
                $el.append($("<option></option>")
                    .attr("value", value).text(key));
            });
        }
    });
});

// Set up a Dish table in modal window
$(document).ready(function () {
    var table = $('#dTable').DataTable({
        "ajax": {
            "url": "/waiter/get_dishes",
            "type": "POST",
            "dataType": "json"
        },
        serverSide: true,
        columnDefs: [
            { "width": "5%", "targets": 0 },
            { "width": "30%", "targets": 1 },
            { "width": "30%", "targets": 2 },
            { "width": "10%", "targets": 3 },
            { "width": "10%", "targets": 4 },
            { "width": "15%", "targets": 5 }
        ],
        columns: [
            { "data": "id", "name": "id",  "title": "id", "visible": false},
            { "data": "name", "name": "name", "title": "Dish"},
            { "data": "category", "name": "category", "title": "Category"},
            { "data": "weight", "name": "weight", "title": "Weight, g"},
            { "data": "price", "name": "price", "title": "Price, $"},

            {"data": null, "defaultContent": "<button>Add</button>"}
        ]
    });

    // Adds a selected dish to the order
    $('#dTable tbody').on('click', 'button', function () {
        var data = table.row($(this).parents('tr')).data();
        var t = $('#odTable').DataTable();
        t.row.add(
            {"id":data.id,
            "name":data.name,
            "quantity": 1,
            "price":data.price,
            "defaultContent": '<button type="button" class="btn btn-default" id="delRow" name ="delRow">Del</button>'}
            ).draw(true);
    });
});

// Submits data to the server
$(document).ready(function () {
    $('#orderForm').submit(function (event) {
        var order = new Object();
        var id = $('#id').val();
        if (id==0) {
            order.openedTimeStamp = new Date();
            order.waiter = $('#waiterId').val();
        }
        order.id = id;
        order.table = $('#table').val();
        order.dishes = getDishes();
        $.ajax({
            url: "/waiter/edit_order",
            dataType: 'json',
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(order),
            success: function (data) {
                console.log("Successfully created order!");
                cleanFeedback();
                displaySuccess(data);
                reloadOrdersTable();
                sendMessage(data);
            },
            error: function (e) {
                console.log("ERROR: ", e);
                cleanFeedback();
                displayError(e);
            },
            done: function (e) {
                console.log("DONE");
            }
        });
        event.preventDefault();
    });

    function sendMessage(data) {
        var stompClient = null;
        var socket = new SockJS('/messaging/waiter');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            stompClient.send("/app/messaging/waiter", {}, JSON.stringify(
                {'time': 'set on server', "action": "created", 'order': ' Order# '+data.id,  "waiter": ' by '+data.waiterName}
                )
            );
            stompClient.disconnect();
            //location.href = '/waiter/orders/today';
        });
    }

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

   function cleanFeedback() {
        $('#feedback').empty().removeClass();
    }

    // Displays the server's feedback on the page
    function displayError(data) {
        $("#feedback").addClass("alert alert-danger");
        var json = "<h4>Error</h4><pre>"
            + data.responseText + "</pre>";
        $('#feedback').html(json);
    }

    function displaySuccess(data) {
        console.log("Success:"+JSON.stringify(data));
        $("#feedback").addClass("alert alert-success");
        var json = "<h4>Success</h4><pre>"
            + JSON.stringify(data) + "</pre>";
        $('#feedback').html(json);
    }
});

// Makes the modal window draggable
$(document).ready(function () {
    $("#myModal").draggable({
        handle: ".modal-header"
    })
});

$(document).ready(function () {
    $("#modal").draggable({
        handle: ".modal-header"
    })
});
