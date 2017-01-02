package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.dataTables.*;
import com.goit.popov.restaurant.service.DishService;
import com.goit.popov.restaurant.service.OrderService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.Arrays;
import java.util.List;


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

        // Auxiliary data source for creating Orders
        @PostMapping("/get_dishes")
        @ResponseBody
        public DataTablesOutputDTOListWrapper<Dish> getDishes() {
                DataTablesOutputDTOListWrapper<Dish> data = new DataTablesOutputDTOListWrapper<>();
                data.setData(dishService.getAll());
                return data;
        }

        // Auxiliary data source for fetching Order's existing dishes
        @PostMapping("/get_orders_dishes")
        @ResponseBody
        public DataTablesOutputDTOCollectionWrapper getOrdersDishes(@RequestParam Integer orderId) throws JsonProcessingException {
                DataTablesOutputDTOCollectionWrapper data = new DataTablesOutputDTOCollectionWrapper();
                data.setData(orderService.toJSON(orderService.getDishes(orderId)));
                ObjectMapper mapper = new ObjectMapper();
                logger.info("Dishes of this Order : "+mapper.writeValueAsString(data));
                return data;
        }

        // Auxiliary data source for fetching all existing tables in the restaurant
        @PostMapping("/get_tables")
        @ResponseBody
        public List<Integer> getTables() {
                return Arrays.asList(orderService.getTables());
        }

        // Auxiliary data source for fetching the Order's table
        @PostMapping("/get_orders_table")
        @ResponseBody
        public int getOrdersTable(@RequestParam Integer orderId) {
                int table = orderService.getById(orderId).getTable();
                return table;
        }

        // Create (Page)
        @GetMapping("/new_order")
        public String showOrderForm() {
                return "th/new_order";
        }

        // Read All (Page)
        @GetMapping(value = "/orders")
        public String showOrdersTable() {
                return "th/orders";
        }

        // Read All (Action): server-side search, paging and sorting
        @PostMapping(value = "/get_orders")
        @ResponseBody
        public DataTablesOutputDTOUniversal<Order> getOrders(DataTablesInputDTO input) throws JsonProcessingException {
                logger.info("Input: " + input);
                DataTablesOutputDTOUniversal<Order> data = orderService.getAll(input);
                ObjectMapper mapper = new ObjectMapper();
                logger.info("Output: " + mapper.writeValueAsString(data));
                return data;
        }

        // Update (Page)
        @GetMapping("/edit_order")
        public ModelAndView editOrder(@RequestParam int id) {
                ModelAndView modelAndView = new ModelAndView("th/edit_order");
                modelAndView.addObject("id", id);
                logger.info("Edit Order, id= "+id);
                return modelAndView;
        }

        // Create Or Update (Action)
        @PostMapping(value="/edit_order")
        public ResponseEntity createOrUpdateOrder(@RequestBody Order order) {
                logger.info("id= "+order.getId());
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
                        if (order.getId()==0) {
                                orderService.insert(order);
                                logger.info("New Order inserted: "+order);
                        } else {
                                orderService.update(order);
                                logger.info("Existing Order updated: "+order);
                        }
                } catch (Exception e) {
                        logger.error("Error: failed to save the Order into DB! Order: "+order);
                        return new ResponseEntity("Error: failed to save the Order into DB!",
                                HttpStatus.EXPECTATION_FAILED);
                }
                return new ResponseEntity("{\"Result\": \"Success\"}", HttpStatus.OK);
        }

        // Delete (Action)
        @GetMapping("/delete_order")
        public String deleteOrder(@RequestParam int id) {
                try {
                        orderService.delete(id);
                        logger.info("Deleted Order, id= "+id);
                } catch (Exception e) {
                        logger.error("Failed to delete this order!");
                        return "th/error";
                }
                return "redirect:/orders";
        }

        // Close (Action)
        @GetMapping("/close_order")
        public String closeOrder(@RequestParam int id) {
                orderService.closeOrder(id);
                logger.info("Order (id= : "+id + ") closed");
                return "redirect:/orders";
        }
}
