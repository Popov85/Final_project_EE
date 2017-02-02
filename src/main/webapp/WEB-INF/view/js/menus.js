/**
 * Created by Andrey on 02.02.2017.
 */
$(document).ready(function () {
    var table = $('#menu').DataTable({
        "bFilter":false,
        "bLengthChange": false,
        "bAutoWidth": false,
        "ajax" : {
            "url": "/get_menus",
            "type": "POST"
        },
        "columnDefs": [
            { "width": "15%", "targets": 0 },
            { "width": "40%", "targets": 1 },
            { "width": "15%", "targets": 2 },
            { "width": "15%", "targets": 3 },
            { "width": "15%", "targets": 4 }
        ],
        columns: [
            { "data": "id", "name": "id",  "title": "id", "visible": false},
            { "data": "name", "name": "name", "title": "menu"},
            { "data": "price", "name": "price, $", "title": "price, $"},
            { "data": "dishes", "name": "dishes", "title": "dishes"},
            { "data": null, "sortable": false, "render": function(data){
                return '<a href="/show_dishes?id=' + data.id + '"><input type="button" class="btn btn-default" value="Show"/></a>';
            }
            }
        ]
    });
});
