package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.service.messages.OrderMessage;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Andrey on 22.02.2017.
 */
@Controller
public class WebSocketController {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(WebSocketController.class);

        /*private final SimpMessagingTemplate simpMessagingTemplate;

        public WebSocketController(SimpMessagingTemplate simpMessagingTemplate) {
                this.simpMessagingTemplate = simpMessagingTemplate;
        }*/

        @MessageMapping("/messaging/waiter")
        @SendTo("/topic/chef")
        public ResponseEntity orderNotify(OrderMessage message) throws Exception {
                message.setTime(new SimpleDateFormat("HH:mm").format(new Date()));
                LOGGER.info("message: "+message);
                return new ResponseEntity(message, HttpStatus.OK);
        }

        /*@MessageMapping("/messaging/chef")
        //@SendToUser("/queue/waiter") // /user/{username} - will be prepended
        public ResponseEntity preparedNotify(PreparedDishMessage message, Principal principal) throws Exception {
                simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/queue/waiter", message);
                simpMessagingTemplate.convertAndSend("/queue/waiter"+message.getToWaiter(), message);
                return new ResponseEntity(message, HttpStatus.OK);
        }*/

        /*private String extractTime (String dateTime) throws ParseException {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                Date date = dateFormat.parse(dateTime);
                String time = new SimpleDateFormat("HH:mm").format(date);
                return time;
        }*/

}
