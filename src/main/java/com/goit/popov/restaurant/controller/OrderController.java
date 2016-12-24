package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.dataTablesDTO.*;
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
import java.util.Arrays;
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

        // Auxiliary data source for creating Orders
        @PostMapping("/get_dishes")
        @ResponseBody
        public DataTablesOutputDTOArrayWrapper<Dish> getDishes() {
                DataTablesOutputDTOArrayWrapper<Dish> data = new DataTablesOutputDTOArrayWrapper<>();
                data.setData(dishService.getAll());
                return data;
        }

        // Auxiliary data source for fetching Order's existing dishes
        @PostMapping("/get_orders_dishes")
        @ResponseBody
        public DataTablesOutputDTOMapWrapper getOrderDishes(@RequestParam Integer orderId) throws JsonProcessingException {
                DataTablesOutputDTOMapWrapper data = new DataTablesOutputDTOMapWrapper();
                data.setData(orderService.toJSON(orderService.getDishes(orderId)));
                ObjectMapper mapper = new ObjectMapper();
                logger.info("Dishes of this Order : "+mapper.writeValueAsString(data));
                return data;
        }

        // Auxiliary data source for fetching all existing tables in the restaurant
        @PostMapping("/get_tables")
        @ResponseBody
        public List<Integer>  getTables() {
                return Arrays.asList(orderService.getTables());
        }

        // Create (Page)
        @GetMapping("/new_order_ajax")
        public ModelAndView showOrderFormAjax() {
                ModelAndView modelAndView = new ModelAndView("th/new_order_ajax");
                modelAndView.addObject("allTables", Order.TABLE_SET);
                return modelAndView;
        }

        // Create (Action)
        @PostMapping(value="/create_order_ajax")
        public ResponseEntity createOrder(@Valid @RequestBody Order order, BindingResult result) {
                logger.info("New Order requested: "+order+" dishes: "+order.getDishes());
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
                logger.info("New Order inserted");
                return new ResponseEntity("{\"Result\": \"Success\"}", HttpStatus.OK);
        }


        // Read All (Page)
        @GetMapping(value = "/orders")
        public String getOrders() {
                return "th/orders_js_server_side";
        }

        // Read All (Action): server-side search, paging and sorting
        @PostMapping(value = "/all_orders")
        @ResponseBody
        public DataTablesOutputDTOUniversal<Order> getOrdersForDataTables(DataTablesInputDTO input) throws JsonProcessingException {
                logger.info("Input: " + input);
                DataTablesOutputDTOUniversal<Order> data = orderService.getAllOrders(input);
                ObjectMapper mapper = new ObjectMapper();
                logger.info("Output: " + mapper.writeValueAsString(data));
                return data;
        }

        // Update (Page) TODO
        @GetMapping("/edit_order")
        public ModelAndView editOrderAjax(@RequestParam int id) {
                ModelAndView modelAndView = new ModelAndView("th/edit_order_ajax");
                modelAndView.addObject("id", id);
                logger.info("id="+id);
                return modelAndView;
        }

        // Update (Page) TODO
        @GetMapping("/edit_order_ajax/{id}")
        public ModelAndView editOrder(@PathVariable("id") int id) throws JsonProcessingException {
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

        // Update (Action) TODO
        @PostMapping(value="/update_order_ajax")
        public ResponseEntity updateOrder(@RequestBody Order order) {
                logger.info("Existing Order requested: "+order+" dishes: "+order.getDishes());
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
                        orderService.update(order);
                } catch (Exception e) {
                        logger.error("Error: failed to insert to DB!");
                        return new ResponseEntity("Failed to update the order in DB",
                                HttpStatus.EXPECTATION_FAILED);
                }
                logger.info("Order: id= "+order.getId()+" was updated");
                return new ResponseEntity("{\"Result\": \"Success\"}", HttpStatus.OK);
        }


        // Create Or Update (Action)
        @PostMapping(value="/edit_order")
        public ResponseEntity createOrUpdateOrder(@RequestBody Order order) {
                logger.info("Order requested: "+order);
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
                        logger.info("id= "+order.getId());
                        if (order.getId()==0) {
                                orderService.insert(order);
                                logger.info("New Order inserted");
                        } else {
                                orderService.update(order);
                                logger.info("Existing Order updated");
                        }

                } catch (Exception e) {
                        logger.error("Error: failed to insert to DB!");
                        return new ResponseEntity("Failed to update the order in DB",
                                HttpStatus.EXPECTATION_FAILED);
                }
                return new ResponseEntity("{\"Result\": \"Success\"}", HttpStatus.OK);
        }

        // Delete (Action)
        @GetMapping("/delete_order")
        public String delete(@RequestParam int id) {
                try {
                        orderService.delete(id);
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
                logger.info("Order (id= : "+id + ") has been closed");
                return "redirect:/orders";
        }

        // ---------------------@Deprecated mappings --------------------


        // @Deprecated (because of legacy DataTablesOutputDTO)
        // Read All (Action): server-side search, paging and sorting
        @PostMapping(value = "/get_orders_ajax")
        @ResponseBody
        public DataTablesOutputDTO getOrdersForDataTables(DataTablesInputDTO input, HttpServletRequest request){
            logger.info("Input: " + input);
            DataTablesOutputDTO output = orderService.getAll(input);
            logger.info("Output: " + output);
            return output;
        }

        // @Deprecated (because of server-side rendering)
        // Read All Page
        @GetMapping(value = "/test_orders")
        public String testOrders(Map<String, Object> model) {
                model.put("orders", orderService.getAll());
                return "th/orders_th_server_side";
        }
}
