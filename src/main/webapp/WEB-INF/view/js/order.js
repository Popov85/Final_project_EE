// Set up an empty Order table
$(document).ready(function () {
    var t = $('#odTable').DataTable({
        columnDefs: [
            {targets: [0], visible: false},
            {targets: '_all', visible: true}
        ]
    });

    // Removes a selected dish from order
    $('#odTable tbody').on('click', 'button', function () {
        t.row($(this).parents('tr')).remove().draw();
    });
});

// Set up a Dish table in modal window
$(document).ready(function () {
    var data = $('#dishes').data("dishes");
    var table = $('#dTable').DataTable({
        "aaData": data,
        "aoColumns": [
            {"mData": "id", "visible": false, "searchable": false},
            {"mData": "name"},
            {"mData": "category"},
            {"mData": "price"},
            {"mData": "weight"},
            {"defaultContent": "<button>Add</button>"}
        ],
        "paging": true,
        "pageLength": 5,
        "ordering": true,
        "order": [1, "asc"]
    });

    // Adds a selected dish to the order
    $('#dTable tbody').on('click', 'button', function () {
        var data = table.row($(this).parents('tr')).data();
        var t = $('#odTable').DataTable();
        t.row.add([
            data.id,
            data.name,
            '<input type="text" value = "1" size = "2" name="input" onchange="alert(\'' + data.price + '\')"/>',
            data.price,
            '<button type="button" class="btn btn-default" id="delRow" name ="delRow">Del</button>'
        ]).draw(true);
    });
});

// Submits data to the server
$(document).ready(function () {
    $('#newOrderForm').submit(function (event) {
        var json = new Object();
        json.id = 1000;
        json.isOpened = true;
        json.openedTimeStamp = new Date();
        json.closedTimeStamp = null;
        json.table = $('#table').val();
        json.waiter = 2; // Mr . Black
        json.dishes = getDishes();
        //alert(JSON.stringify(json));
        $.ajax({
            url: $("#newOrderForm").attr( "action"),
            dataType: 'json',
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(json),
            success: function (data) {
                console.log("Success");
                location.href='/orders';
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