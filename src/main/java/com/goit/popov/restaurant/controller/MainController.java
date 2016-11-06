package com.goit.popov.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.Map;

/**
 * Created by Andrey on 11/6/2016.
 */
@Controller
public class MainController {

        @RequestMapping(value = "/", method = RequestMethod.GET)
        public String index(Map<String, Object> model) {
                model.put("currentDate", new Date().toString());
                return "index";
        }
}
