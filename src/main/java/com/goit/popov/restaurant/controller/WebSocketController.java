package com.goit.popov.restaurant.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Andrey on 22.02.2017.
 */
@Controller
public class WebSocketController {

        @MessageMapping("/messaging/chef")
        @SendTo("/topic/waiter")
        public ResponseEntity orderNotify(String message) throws Exception {
                System.out.println("Controller method is invoked!");
                String time = new SimpleDateFormat("HH:mm").format(new Date());
                return new ResponseEntity("{\"Result\": \"Success\"}", HttpStatus.OK);
        }

}
