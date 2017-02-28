/**
 * Created by Andrey on 23.02.2017.
 */
$(document).ready(function () {

    var stompClient = null;
    var socket = new SockJS('/messaging/waiter');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/chef', function (message) {
            showMessage(JSON.parse(message.body));
        });
    });

    function showMessage(message) {
        console.log("Accepted from a waiter!"+JSON.stringify(message));
        $('#messages').append(message.body.time +' '+message.body.order+' '+message.body.action+' '
            +(message.body.waiter!=null ? message.body.waiter : "")+ '&#xA;');
        reloadOrdersTable();
    }
});

function reloadOrdersTable() {
    $('#ordsTable').DataTable()
        .ajax.url(
        "/chef/get_orders_today"
    ).load()
}