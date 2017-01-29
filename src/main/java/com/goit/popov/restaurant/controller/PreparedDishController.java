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

        private static final Logger logger = (Logger) LoggerFactory.getLogger(PreparedDishController.class);

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
        public DataTablesOutputDTOCollectionWrapper getOrdersPreparedDishes(@RequestParam int orderId) {
                DataTablesOutputDTOCollectionWrapper data = new DataTablesOutputDTOCollectionWrapper();
                Order order = orderService.getById(orderId);
                data.setData(preparedDishService.toJSON(order));
                return data;
        }

        @PostMapping("/chef/check_order")
        public ResponseEntity checkOrder(@RequestParam int dishId, @RequestParam int quantity, @RequestParam int orderId) {
                Order order = orderService.getById(orderId);
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode node = mapper.createObjectNode();
                node.put("dishId", dishId);
                node.put("quantity", quantity);
                node.put("orderId", orderId);
                if (order.isCancelled()) {
                        node.put("isCancelled", true);
                } else {
                        node.put("isCancelled", false);
                }
                return new ResponseEntity(node, HttpStatus.OK);
        }

        @GetMapping("/chef/confirm_dishes_prepared")
        public ResponseEntity confirmDishPrepared(@RequestParam int dishId, @RequestParam int quantity, @RequestParam int orderId) {
                int chefId = getChef();
                preparedDishService.confirmDishesPrepared(dishId, quantity, orderId, chefId);
                return new ResponseEntity("{\"orderId\":" +orderId+"}", HttpStatus.OK);
        }

        @GetMapping("/chef/confirm_dishes_cancelled")
        public ResponseEntity confirmDishCancelled(@RequestParam int dishId, @RequestParam int quantity, @RequestParam int orderId) {
                int chefId = getChef();
                preparedDishService.confirmDishesCancelled(dishId, quantity, orderId, chefId);
                return new ResponseEntity("{\"orderId\":" +orderId+"}", HttpStatus.OK);
        }

        private int getChef() {
                int chefId;
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                try {
                        Employee userDetails = (Employee) auth.getPrincipal();
                        chefId = userDetails.getId();
                } catch (Exception e) {
                        logger.error("ERROR: "+e.getMessage());
                        // TODO throw an appropriate exception!
                        return 1;
                }
                return chefId;
        }
}
