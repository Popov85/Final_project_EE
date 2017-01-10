/**
 * Created by Andrey on 1/10/2017.
 */
$(document).ready(function () {
    var id = parseInt($('#dishId').val());
    console.log("id = "+id);
    var table = $('#ingredients').DataTable({
        "bFilter":false,
        "bLengthChange": false,
        "ajax" : {
            "url": "/get_dishs_ingredients?id="+id,
            "type": "POST"
        },
        "columnDefs": [
            { "width": "10%", "targets": 0 },
            { "width": "60%", "targets": 1 },
            { "width": "30%", "targets": 2 }
        ],
        columns: [
            { "data": "id", "name": "id",  "title": "id", "visible": true},
            { "data": "name", "name": "name", "title": "ingredient"},
            { "data": "quantity", "name": "quantity, g", "title": "quantity, g"},

        ]
    });
});
