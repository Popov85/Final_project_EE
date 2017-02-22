package com.goit.popov.restaurant.controller;

import com.goit.popov.restaurant.model.Message;
import com.goit.popov.restaurant.model.OutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Andrey on 22.02.2017.
 */
@Controller
public class WebSocketController {

        @GetMapping("/start")
        public String getChat() {
                return "th/chat";
        }

        @MessageMapping("/chat")
        @SendTo("/topic/messages")
        public OutputMessage send(Message message) throws Exception {
                String time = new SimpleDateFormat("HH:mm").format(new Date());
                return new OutputMessage(message.getFrom(), message.getText(), time);
        }

}
