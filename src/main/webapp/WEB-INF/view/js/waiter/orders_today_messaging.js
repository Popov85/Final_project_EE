/**
 * Created by Andrey on 2/26/2017.
 */
$(document).ready(function () {

    var stompClient = null;

    var socket = new SockJS('/messaging/chef');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/user/queue/waiter', function (message) {
            showMessage(JSON.parse(message.body));
        });
    });

    function showMessage(message) {
        console.log("Accepted from subscription!"+JSON.stringify(message));
        $('#messages').append(message.time +' Order# '+message.order+' '+message.action+
            ' '+message.dish+' ('+message.quantity+') '+' by '+message.chef +'&#xA;');
        // Reload Orders table
        reloadOrdersTable();
    }
});
