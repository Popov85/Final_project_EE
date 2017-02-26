/**
 * Created by Andrey on 23.02.2017.
 */
$(document).ready(function () {

    var stompClient = null;

    var socket = new SockJS('/messaging/chef');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/waiter', function (messageOutput) {
            showMessageOutput(JSON.parse(messageOutput.body));
        });
    });

    function showMessageOutput(messageOutput) {
        console.log("Accepted from subscription!"+JSON.stringify(messageOutput));
        $('#messages').append(messageOutput.body.time +' '+messageOutput.body.order+' '+messageOutput.body.action+' '+messageOutput.body.waiter+ '&#xA;');
    }
});