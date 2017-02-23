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
            console.log("Accept from subscription!");
            //showMessageOutput(JSON.parse(messageOutput.body));
            showMessageOutput(messageOutput);
        });
    });

    function showMessageOutput(messageOutput) {
        alert("Happened!");
        $('#messages').append(messageOutput + '&#xA;');
    }
});