package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.service.DishService;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Andrey on 02.12.2016.
 */
@Controller
public class OrderController {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(OrderController.class);

        @Autowired
        private DishService dishService;

        @RequestMapping(value="/new_order")
        public String getAllDishes(Locale locale, Map<String, Object> model) throws IOException {
                logger.info("Welcome to Order! The locale is {}.", locale);
                ObjectMapper mapper = new ObjectMapper();
                model.put("dishes", mapper.writeValueAsString(dishService.getAll()));

                return "th/order";
        }
}
