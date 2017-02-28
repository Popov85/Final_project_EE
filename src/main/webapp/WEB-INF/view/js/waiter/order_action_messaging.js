/**
 * Created by Andrey on 28.02.2017.
 */

function sendMessage(data, action) {
    var stompClient = null;
    var socket = new SockJS('/messaging/waiter');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.send("/app/messaging/waiter", {}, JSON.stringify(
            //{'time': moment(new Date()).format('HH:mm'), "action": action, 'order': ' Order# '+data.id,  "waiter": ' by '+data.waiterName}
            )
        );
        stompClient.disconnect();
        location.href = '/waiter/orders/today';
    });
}
