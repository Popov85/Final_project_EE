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
        console.log("Accepted from chef!"+JSON.stringify(message));
        var messageBar = $('#messages');
        messageBar.append(message.time +' Order# '+message.order+' '+message.action+
            ' '+message.dish+' ('+message.quantity+') '+' by '+message.chef +'&#xA;');
        if(messageBar.length) messageBar.scrollTop(messageBar[0].scrollHeight - messageBar.height());
        reloadOrdersTable();
    }
});
