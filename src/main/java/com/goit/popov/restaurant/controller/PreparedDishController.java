package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.model.PreparedDish;
import com.goit.popov.restaurant.service.OrderService;
import com.goit.popov.restaurant.service.PreparedDishService;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOCollectionWrapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

        // Show (Page)
        @GetMapping("/cook/prepared_dishes")
        public String showPreparedDishForm() {
                return "th/chef/prepared_dishes";
        }

        @GetMapping("/get_prepared_dishes")
        @ResponseBody
        public List<PreparedDish> getPreparedDishes() {
                logger.info("List: "+preparedDishService.getAll());
                return preparedDishService.getAll();
        }

        @PostMapping("/get_orders_for_chef")
        @ResponseBody
        public DataTablesOutputDTOCollectionWrapper getOrdersForChef() throws JsonProcessingException {
                DataTablesOutputDTOCollectionWrapper data = new DataTablesOutputDTOCollectionWrapper();
                data.setData(preparedDishService.toJSON(preparedDishService.getAllOrdersForChef()));
                ObjectMapper mapper = new ObjectMapper();
                logger.info("Orders for chef view: "+mapper.writeValueAsString(data));
                return data;
        }

        @GetMapping("/get_orders_prepared_dishes")
        @ResponseBody
        public DataTablesOutputDTOCollectionWrapper getOrdersPreparedDishes(@RequestParam int orderId) throws JsonProcessingException {
                DataTablesOutputDTOCollectionWrapper data = new DataTablesOutputDTOCollectionWrapper();
                Order order = orderService.getById(orderId);
                data.setData(preparedDishService.toJSON(order));
                ObjectMapper mapper = new ObjectMapper();
                logger.info("Order's prepared dishes: "+mapper.writeValueAsString(data));
                return data;
        }

        @GetMapping("/today/get_prepared_dishes")
        @ResponseBody
        public List<PreparedDish> getAllChefToday(@RequestParam int chefId) {
                logger.info("List: "+preparedDishService.getAllChefToday(chefId));
                return preparedDishService.getAllChefToday(chefId);
        }

}
