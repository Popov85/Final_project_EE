package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.DishService;
import com.goit.popov.restaurant.service.OrderService;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 02.12.2016.
 */
@Controller
public class OrderController {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(OrderController.class);

        @Autowired
        private DishService dishService;

        @Autowired
        private OrderService orderService;

        @GetMapping("/new_order")
        public ModelAndView showOrderForm(){
                logger.info("Show Order form");
                ModelAndView modelAndView = new ModelAndView("th/new_order");
                Order order = new Order();
                order.setOpened(true);
                order.setTable(0);
                order.setOpenedTimeStamp(new Date());
                modelAndView.addObject("order", order);
                List<Integer> tables = new ArrayList<>();
                for (int i = 0; i < Order.TABLE_SET.length; i++) {
                        tables.add(Order.TABLE_SET[i]);
                }
                modelAndView.addObject("allTables", tables);
                Map<Dish, Integer> orderedDishes;
                orderedDishes = orderService.getDishes(1);
                modelAndView.addObject("orderedDishes", orderedDishes);
                return modelAndView;
        }

        @GetMapping(value="/show_dishes")
        public String getAllDishes(Map<String, Object> model) throws IOException {
                ObjectMapper mapper = new ObjectMapper();
                model.put("dishes", mapper.writeValueAsString(dishService.getAll()));
                logger.info("JSON is: "+mapper.writeValueAsString(dishService.getAll()));
                return "th/order";
        }

        @GetMapping(value="/show_orders")
        public String getAllOrders(Map<String, Object> model) {
                model.put("orders", orderService.getAll());
                return "th/orders";
        }


        @GetMapping(value="/select_table")
        public String selectTable(Map<String, Object> model) {

                return "th/new_order";
        }


        @GetMapping(value="/delete_dish")
        public String deleteDish(Map<String, Object> model) {

                return "th/new_order";
        }
}
