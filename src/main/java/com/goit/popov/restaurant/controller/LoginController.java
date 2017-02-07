package com.goit.popov.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Andrey on 1/7/2017.
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        return "th/login";
    }

    @GetMapping("/access_denied")
    public ModelAndView showAccessDeniedPage(ModelAndView modelAndView) {
        modelAndView.addObject("error","Error");
        modelAndView.setViewName("th/login");
        return modelAndView;
    }
}
