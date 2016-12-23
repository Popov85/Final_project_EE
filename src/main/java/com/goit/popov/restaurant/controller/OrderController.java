package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.dataTablesDTO.DataTablesInputDTO;
import com.goit.popov.restaurant.service.dataTablesDTO.DataTablesOutputDTO;
import com.goit.popov.restaurant.service.DishService;
import com.goit.popov.restaurant.service.OrderService;
import com.goit.popov.restaurant.service.dataTablesDTO.DataTablesOutputDTOArrayWrapper;
import com.goit.popov.restaurant.service.dataTablesDTO.DataTablesOutputDTOUniversal;
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

        // Auxiliary data source for creating Orders
        @PostMapping("/get_dishes")
        @ResponseBody
        public DataTablesOutputDTOArrayWrapper<Dish> getDishes() {
                DataTablesOutputDTOArrayWrapper<Dish> data = new DataTablesOutputDTOArrayWrapper<>();
                data.setData(dishService.getAll());
                return data;
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

        // Delete (Action)
        @GetMapping("/delete_order")
        public String delete(@RequestParam int id) {
                orderService.delete(id);
                return "redirect:/orders_js_server_side";
        }

        // Close (Action)
        @GetMapping("/close_order")
        public String closeOrder(@RequestParam int id) {
                orderService.closeOrder(id);
                logger.info("Order (id= : "+id + ") has been closed");
                return "redirect:/test_orders";
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
