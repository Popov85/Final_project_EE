/**
 * Created by Andrey on 2/26/2017.
 */
$(document).ready(function () {

    var stompClient = null;

    var socket = new SockJS('/messaging/chef');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/user/queue/waiter', function (messageOutput) {
            showMessageOutput(JSON.parse(messageOutput.body));
        });
    });

    function showMessageOutput(messageOutput) {
        console.log("Accepted from subscription!"+JSON.stringify(messageOutput));
        $('#messages').append(messageOutput.time +' Order# '+messageOutput.order+' '+messageOutput.action+
            ' '+messageOutput.dish+' ('+messageOutput.quantity+') '+' by '+messageOutput.chef +'&#xA;');
        // Reload Orders table
        reloadOrdersTable();
    }
});

function reloadOrdersTable() {
    $('#ordsTable').DataTable()
        .ajax.url(
        "/waiter/get_orders?waiterId="+parseInt($('#waiterId').val())
    ).load()
}