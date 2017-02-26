/**
 * Created by Andrey on 2/26/2017.
 */
$(document).ready(function () {

    var stompClient = null;

    var socket = new SockJS('/messaging/chef');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);


        /*stompClient.subscribe('/user/queue/waiter', function (messageOutput) {
            console.log("Waiter has received the message!");
            showMessageOutput(JSON.parse(messageOutput.body));
        });*/

        stompClient.subscribe('/queue/waiter'+$('#username').val(), function (messageOutput) {
            console.log("Waiter by name has received the message!");
            showMessageOutput(JSON.parse(messageOutput.body));
        });

        /*stompClient.subscribe('/queue/waiter'+$('#waiterId').val(), function (messageOutput) {
            console.log("Waiter by id has received the message!");
            showMessageOutput(JSON.parse(messageOutput.body));
        });*/
    });

    function showMessageOutput(messageOutput) {
        console.log("Accepted from subscription!"+JSON.stringify(messageOutput));
        $('#messages').append(messageOutput.time +' Order# '+messageOutput.order+' '+messageOutput.action+
            ' '+messageOutput.dish+' ('+messageOutput.quantity+') '+' by '+messageOutput.byChef +'&#xA;');
        // TODO reload table
    }
});
