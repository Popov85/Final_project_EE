package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.DataTablesDTO.DataTablesInputDTO;
import com.goit.popov.restaurant.service.DataTablesDTO.DataTablesOutputDTO;
import com.goit.popov.restaurant.service.DishService;
import com.goit.popov.restaurant.service.OrderService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

        @GetMapping("/get_dishes")
        @ResponseBody
        public List<Dish> getDishes() {
               return dishService.getAll();
        }

        // Read All
        @GetMapping(value = "/orders")
        public String orders(Map<String, Object> model) {
                model.put("orders", orderService.getAll());
                return "th/orders";
        }

        @GetMapping("/new_order_ajax")
        public ModelAndView showOrderFormAjax() throws JsonProcessingException {
                ModelAndView modelAndView = new ModelAndView("th/new_order_ajax");
                modelAndView.addObject("allTables", Order.TABLE_SET);
                List<Dish> dishes = dishService.getAll();
                ObjectMapper mapper = new ObjectMapper();
                modelAndView.addObject("dishes", mapper.writeValueAsString(dishes));
                return modelAndView;
        }

        @GetMapping("/edit_order_ajax/{id}")
        public ModelAndView editOrderAjax(@PathVariable("id") int id) throws JsonProcessingException {
                ModelAndView modelAndView = new ModelAndView("th/edit_order_ajax");
                modelAndView.addObject("allTables", Order.TABLE_SET);
                List<Dish> dishes = dishService.getAll();
                ObjectMapper mapper = new ObjectMapper();
                modelAndView.addObject("dishes", mapper.writeValueAsString(dishes));
                Map<Dish, Integer> orderedDishes;
                orderedDishes = orderService.getDishes(id);
                modelAndView.addObject("orderedDishes", orderedDishes);
                return modelAndView;
        }

        // Insert new Order
        @PostMapping(value="/create_order_ajax")
        public ResponseEntity createOrder(@Valid @RequestBody Order order, BindingResult result) {
                logger.info("New Order created: "+order+" dishes: "+order.getDishes());
                // 1. If dish array is empty, return - Error
                if (order.getDishes().isEmpty()) {
                        logger.error("Error: no dishes!");
                        return new ResponseEntity("Order must contain dishes!",
                                HttpStatus.EXPECTATION_FAILED);
                }
                // 2. Check if there is enough ingredients to fulfill an order
                if (!orderService.validateIngredients(order.getDishes())) {
                        logger.error("Error: not enough ingredients!");
                        return new ResponseEntity("Not enough ingredients to fulfill the order",
                                HttpStatus.EXPECTATION_FAILED);
                }
                try {
                        orderService.insert(order);
                } catch (Exception e) {
                        logger.error("Error: failed to insert to DB!");
                        return new ResponseEntity("Failed to insert this order to DB",
                                HttpStatus.EXPECTATION_FAILED);
                }
                logger.info("New Order inserted: success");
                return new ResponseEntity("{\"Result\": \"Success\"}", HttpStatus.OK);
        }

        // Close Order
        @GetMapping("/close_order")
        public String closeOrder(@RequestParam int id) {
                orderService.closeOrder(id);
                return "redirect:/orders";
        }

        @PostMapping(value = "/get_orders_ajax")
        @ResponseBody
        public DataTablesOutputDTO getOrdersForDataTables(DataTablesInputDTO input, HttpServletRequest request) throws Exception {
            logger.info("Input: " + input);
            DataTablesOutputDTO output = orderService.getAll(input);
            logger.info("Output: " + output);
            return output;
        }

        @GetMapping(value = "/orders_ajax")
        public String showOrderTable() {
                logger.info("Show empty table");
                return "th/orders_ajax";
        }
}

/*
            Map<String, Object> map = request.getParameterMap();
            logger.info("Parameters:");
            try {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    if (entry.getValue() instanceof String[]) {
                        String[] strArray = (String[]) entry.getValue();
                        System.out.println((entry.getKey() + " : " + Arrays.toString(strArray)));
                    } else {
                        System.out.println((entry.getKey() + " : " + entry.getValue()));
                    }
                }
            } catch (Exception e) {
                logger.error("Failed to go through the map, " + e.getMessage());
            }
 */