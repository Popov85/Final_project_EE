package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Menu;
import com.goit.popov.restaurant.service.MenuService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

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
        public String index(Map<String, List<Menu>> model) {
                model.put("menus", menuService.getAll());
                logger.info("all menus are: "+menuService.getAll().size());
                return "index";
        }

}
