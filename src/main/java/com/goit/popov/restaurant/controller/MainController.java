package com.goit.popov.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Andrey on 11/6/2016.
 */
@Controller
public class MainController {

    //@Autowired
    //private PasswordEncoder encoder;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        //System.out.println("password: "+encoder.encode("root"));
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
