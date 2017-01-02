package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Menu;
import com.goit.popov.restaurant.service.MenuService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by Andrey on 11/6/2016.
 */
@Controller
public class MainController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(MainController.class);

    @Autowired
    private MenuService menuService;

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "th/index";
    }

}
