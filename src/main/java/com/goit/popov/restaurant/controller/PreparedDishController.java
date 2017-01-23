package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.model.PreparedDish;
import com.goit.popov.restaurant.service.OrderService;
import com.goit.popov.restaurant.service.OrderServiceImpl;
import com.goit.popov.restaurant.service.PreparedDishService;
import com.goit.popov.restaurant.service.authentification.Employee;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOCollectionWrapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

        @GetMapping("/chef/archive/get_prepared_dishes")
        @ResponseBody
        public List<PreparedDish> getPreparedDishes() {
                logger.info("Count: "+preparedDishService.count());
                logger.info("List: "+preparedDishService.getAll());
                return preparedDishService.getAll();
        }

        @GetMapping("/get_all_orders_with_prepared_dishes")
        @ResponseBody
        public List<Order> getAllWithPreparedDishes() {
                Order order = orderService.getById(1);
                logger.info("Order #1 : "+order);
                logger.info("validateOrder: "+orderService.validateOrder(order));
                return orderService.getAll();
        }

        @PostMapping("/chef/get_orders_today")
        @ResponseBody
        public DataTablesOutputDTOCollectionWrapper getOrdersForChef() {
                DataTablesOutputDTOCollectionWrapper data = new DataTablesOutputDTOCollectionWrapper();
                data.setData(preparedDishService.toJSON(preparedDishService.getAllOrdersForChef()));
                logger.info("Orders for chef view: "+data);
                return data;
        }

        @GetMapping("/today/get_prepared_dishes")
        @ResponseBody
        public List<PreparedDish> getAllChefToday(@RequestParam int chefId) {
                logger.info("List: "+preparedDishService.getAllChefToday(chefId));
                return preparedDishService.getAllChefToday(chefId);
        }

        @GetMapping("/chef/get_orders_prepared_dishes")
        @ResponseBody
        public DataTablesOutputDTOCollectionWrapper getOrdersPreparedDishes(@RequestParam int orderId) {
                DataTablesOutputDTOCollectionWrapper data = new DataTablesOutputDTOCollectionWrapper();
                Order order = orderService.getById(orderId);
                data.setData(preparedDishService.toJSON(order));
                logger.info("Order's prepared dishes: "+data);
                return data;
        }

        @GetMapping("/chef/confirm_dish_prepared")
        public String confirmDishPrepared(@RequestParam int dishId, @RequestParam int quantity, @RequestParam int orderId) {
                int chefId;
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                try {
                        Employee userDetails = (Employee) auth.getPrincipal();
                        chefId = userDetails.getId();
                } catch (Exception e) {
                        logger.error("ERROR: "+e.getMessage());
                        chefId = 1;
                }
                logger.info("Proceeds... after possible exception caught: chefId= "+chefId);
                preparedDishService.confirmDishPrepared(dishId, quantity, orderId, chefId);
                return "redirect:/chef/prepared_dishes";
        }
}
