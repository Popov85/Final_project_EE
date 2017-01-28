/**
 * Created by Andrey on 1/16/2017.
 */
$(document).ready(function () {

    function prepare(url) {
        console.log("URL: ");
        console.log("success function: ");
        return 3;
    }

});

$(document).ready(function () {
    var table = $('#ordsTable').DataTable({
        "ajax": {
            "url": "/chef/get_orders_today",
            "type": "POST",
            "dataType": "json"
        },
        columnDefs: [
            {"width": "5%", "targets": 0},
            {"width": "30%", "targets": 1},
            {"width": "40%", "targets": 2},
            {"width": "10%", "targets": 5}
        ],
        columns: [
            {"data": "id", "name": "id", "title": "id", "visible": true},
            {"data": "waiter", "name": "waiter", "title": "waiter"},
            {"data": "openedTimeStamp", "name": "openedTimeStamp", "title": "opened time"},
            {"data": "dishes", "name": "dishes quantity", "title": "dishes quantity"},
            {"data": "isFulfilled", "name": "isFulfilled", "title": "isFulfilled"},
            {"data": "isCancelled", "name": "isCancelled", "title": "isCancelled"},

            {
                "data": null, "sortable": false, "render": function () {
                return '<button class="btn btn-default">Details</button>';
            }
            }
        ]
    });

    // Onclick on a table row
    $('#ordsTable tbody').on('click', 'button', function () {
        var data = table.row($(this).parents('tr')).data();
        $('#orderId').text(data.id);
        $('#status').text(data.isCancelled);
        //console.log(data.isCancelled);
        var table2 = $('#dishesTable').DataTable()
            .ajax.url(
                "/chef/get_orders_prepared_dishes?orderId="
                + encodeURIComponent(data.id)
            )
            .load()
    });

    $('#dishesTable').DataTable({
        bLengthChange: false,
        columnDefs: [
            {"width": "5%", "targets": 0},
            {"width": "50%", "targets": 1},
            {"width": "10%", "targets": 4}
        ],
        columns: [
            {"data": "id", "name": "id", "title": "id", "visible": true},
            {"data": "dish", "name": "dish", "title": "dish"},
            {"data": "quantity", "name": "quantity", "title": "quantity"},
            {"data": "isPrepared", "name": "isPrepared", "title": "isPrepared"},
            {"data": "isReturned", "name": "isReturned", "title": "isReturned"},

            {
                "data": null, "sortable": false, "render": function (data) {
                if (data.isPrepared || data.isReturned) {
                    return '<input type="button" class="btn btn-default" disabled="true" value="Confirm"/>';
                } else {
                    var params = 'dishId=' + data.id+'&quantity='+data.quantity +'&orderId='+$('#orderId').text();
                    var status = $('#status').text();
                    return '<a href="javascript:prepare(\'' + params + '\')">' +
                                '<input type="button" class="btn btn-default" value="Confirm"/>' +
                            '</a>';
                }
            }
            }
        ]
    });


});