package com.goit.popov.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Andrey on 11/6/2016.
 */
@Controller
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "th/index";
    }

    @RequestMapping(value = "/2", method = RequestMethod.GET)
    public String index2() {
        return "th/index2";
    }

    @GetMapping(value = "/admin")
    public String startManager() {
        return "th/manager/start";
    }

}
