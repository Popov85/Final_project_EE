/**
 * Created by Andrey on 1/10/2017.
 */
$(document).ready(function () {
    var dishId = parseInt($('#dishId').val());
    console.log("id = "+dishId);
    var table = $('#ingredients').DataTable({
        "bFilter":false,
        "bLengthChange": false,
        "ajax" : {
            "url": "/get_dishs_ingredients?dishId="+dishId,
            "type": "POST"
        },
        "columnDefs": [
            { "width": "10%", "targets": 0 },
            { "width": "60%", "targets": 1 },
            { "width": "15%", "targets": 2 },
            { "width": "15%", "targets": 2 }
        ],
        columns: [
            { "data": "id", "name": "id",  "title": "id", "visible": true},
            { "data": "name", "name": "name", "title": "Ingredient"},
            { "data": "quantity", "name": "quantity", "title": "Quantity"},
            { "data": "unit", "name": "unit", "title": "Unit"}
        ]
    });
});
