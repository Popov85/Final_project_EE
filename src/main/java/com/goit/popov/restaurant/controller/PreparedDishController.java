package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.model.PreparedDish;
import com.goit.popov.restaurant.service.OrderService;
import com.goit.popov.restaurant.service.PreparedDishService;
import com.goit.popov.restaurant.service.authentification.Employee;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOCollectionWrapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by Andrey on 1/15/2017.
 */
@Controller
public class PreparedDishController {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(PreparedDishController.class);

        @Autowired
        private PreparedDishService preparedDishService;

        @Autowired
        private OrderService orderService;

        @GetMapping("/chef/prepared_dishes")
        public String showPreparedDishForm() {
                return "th/chef/prepared_dishes";
        }

        @GetMapping("/admin/archive/get_prepared_dishes")
        @ResponseBody
        public List<PreparedDish> getPreparedDishes() {
                return preparedDishService.getAll();
        }

        @PostMapping("/chef/get_orders_today")
        @ResponseBody
        public DataTablesOutputDTOCollectionWrapper getTodayOrdersForChef() {
                DataTablesOutputDTOCollectionWrapper data = new DataTablesOutputDTOCollectionWrapper();
                data.setData(preparedDishService.toJSON(orderService.getAllToday()));
                return data;
        }

        @GetMapping("/chef/get_orders_prepared_dishes")
        @ResponseBody
        public DataTablesOutputDTOCollectionWrapper getOrdersPreparedDishes(@RequestParam Long orderId) {
                DataTablesOutputDTOCollectionWrapper data = new DataTablesOutputDTOCollectionWrapper();
                Order order = orderService.getById(orderId);
                data.setData(preparedDishService.toJSON(order));
                return data;
        }

        @GetMapping("/chef/check_order")
        public ResponseEntity checkOrder(@RequestParam String dish, @RequestParam Long dishId,
                                         @RequestParam int quantity, @RequestParam Long orderId) {
                Order order = orderService.getById(orderId);
                ObjectNode params = populateParams(dish, dishId, quantity, orderId);
                params.put("isCancelled", order.isCancelled());
                return new ResponseEntity(params, HttpStatus.OK);
        }

        @GetMapping("/chef/confirm_dishes_prepared")
        public ResponseEntity confirmDishPrepared(@RequestParam String dish, @RequestParam Long dishId,
                                                  @RequestParam int quantity, @RequestParam Long orderId) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                Employee userDetails = (Employee) auth.getPrincipal();
                Long chefId = userDetails.getId();
                preparedDishService.confirmDishesPrepared(dishId, quantity, orderId, chefId);
                ObjectNode params = populateParams(dish, dishId, quantity, orderId);
                return new ResponseEntity(params, HttpStatus.OK);
        }

        private ObjectNode populateParams(String dish, Long dishId, int quantity, Long orderId) {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode params = mapper.createObjectNode();
                params.put("dish", dish);
                params.put("dishId", dishId);
                params.put("quantity", quantity);
                params.put("orderId", orderId);
                return params;
        }

        @GetMapping("/chef/confirm_dishes_cancelled")
        public ResponseEntity confirmDishCancelled(@RequestParam String dish, @RequestParam Long dishId,
                                                   @RequestParam int quantity, @RequestParam Long orderId) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                Employee userDetails = (Employee) auth.getPrincipal();
                Long chefId = userDetails.getId();
                preparedDishService.confirmDishesCancelled(dishId, quantity, orderId, chefId);
                return new ResponseEntity("{\"orderId\":" +orderId+"}", HttpStatus.OK);
        }
}
