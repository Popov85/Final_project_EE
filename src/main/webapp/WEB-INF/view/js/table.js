/**
 * Created by Andrey on 02.12.2016.
 */

$(document).ready(function() {
    console.log("JS is on operation");
    //alert("Loaded!");
})

$(document).ready(function(){
    var data =eval('${dishes}');
    var table = $('#dTable').DataTable( {
        "aaData": data,
        "aoColumns": [
            { "mData": "id"},
            { "mData": "name"},
            { "mData": "category"},
            { "mData": "price"},
            { "mData": "weight"},
            { "mData": "weight"}
        ],
        "paging":true,
        "pageLength":20,
        "ordering":true
    });
});
