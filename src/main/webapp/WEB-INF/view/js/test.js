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
    var data = $('#dishes').data("myData"); //eval([[${dishes}]]);
    console.log("External file data: ");
    console.log("File: Data is: "+data);
    console.log("File: First object is:  "+data[1]);
    console.log("File: First object's ID = :  "+data[1].id);
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
});

