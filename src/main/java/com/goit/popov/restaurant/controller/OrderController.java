package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.OrderService;
import com.goit.popov.restaurant.service.dataTables.*;
import com.goit.popov.restaurant.service.exceptions.NotEnoughIngredientsException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 02.12.2016.
 */
@Controller
public class OrderController {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(OrderController.class);

        private static final String NO_DISHES_MESSAGE = "Order must contain dishes!";
        private static final String INGREDIENTS_SHORTAGE_MESSAGE = "Not enough ingredients to fulfill the order!";
        private static final String FORBIDDEN_ACTION_MESSAGE = "This action is forbidden!";
        private static final String UNEXPECTED_ERROR_MESSAGE ="Unexpected error happened";

        @Autowired
        private OrderService orderService;

        // Auxiliary data source for fetching Order's existing dishes
        @GetMapping("/waiter/get_orders_dishes")
        @ResponseBody
        public DataTablesOutputDTOCollectionWrapper getOrdersDishes(@RequestParam Long orderId) {
                DataTablesOutputDTOCollectionWrapper data = new DataTablesOutputDTOCollectionWrapper();
                Order order = orderService.getById(orderId);
                Map<Dish, Integer> dishes = new HashMap<>();
                if (order!=null) dishes = order.getDishes();
                data.setData(orderService.toJSON(dishes));
                return data;
        }

        // Auxiliary data source for fetching all existing tables in the restaurant
        @PostMapping("/waiter/get_tables")
        @ResponseBody
        public List<Integer> getTables() {
                return Arrays.asList(orderService.getTables());
        }

        // Auxiliary data source for fetching the Order's table
        @PostMapping("/waiter/get_orders_table")
        @ResponseBody
        public String getOrdersTable(@RequestParam Long orderId) {
                String table = orderService.getById(orderId).getTable();
                return table;
        }

        @GetMapping("/waiter/new_order")
        public String showOrderForm() {
                return "th/waiter/new_order";
        }

        @GetMapping(value = "/admin/orders")
        public String showOrdersTableManager() {
                return "th/manager/orders";
        }

        @GetMapping(value = "/waiter/orders/today")
        public String showOrdersTableWaiter() {
                return "th/waiter/orders_today";
        }

        @GetMapping(value = "/waiter/orders/archive")
        public String showOrdersTableWaiterArchive() {
                return "th/waiter/archive/orders_archive";
        }

        @PostMapping(value = "/admin/get_orders")
        @ResponseBody
        public DataTablesOutputDTOUniversal<Order> getOrders(DataTablesInputExtendedDTO input) {
                DataTablesOutputDTOUniversal<Order> data = orderService.getAllOrders(input);
                return data;
        }

        @PostMapping(value = "/waiter/get_order")
        @ResponseBody
        public Order getOrder(@RequestParam Long orderId) {
                LOGGER.info("Order: "+orderService.getById(orderId));
                return orderService.getById(orderId);
        }

        @PostMapping(value = "/waiter/get_orders")
        @ResponseBody
        public DataTablesOutputDTOListWrapper<Order> getWaiterOrders(@RequestParam Long waiterId) {
                DataTablesOutputDTOListWrapper<Order> data = new DataTablesOutputDTOListWrapper<>();
                        data.setData(orderService.getAllWaiterToday(waiterId));
                return data;
        }

        @PostMapping(value = "/waiter/get_archive")
        @ResponseBody
        public DataTablesOutputDTOUniversal<Order> getWaiterOrdersArchive(@RequestParam Long waiterId,
                                                                          DataTablesInputExtendedDTO input) throws JsonProcessingException {
                DataTablesOutputDTOUniversal<Order> data = orderService.getAllOrdersByWaiter(input, waiterId);
                return data;
        }

        // Update (Page)
        @GetMapping("/waiter/edit_order")
        public ModelAndView editOrder(@RequestParam Long id) {
                ModelAndView modelAndView = new ModelAndView("th/waiter/edit_order");
                modelAndView.addObject("id", id);
                return modelAndView;
        }

        @PostMapping("/waiter/check_order")
        public ResponseEntity checkOrder(@RequestParam Long orderId) {
                Order order = orderService.getById(orderId);
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode params = mapper.createObjectNode();
                params.put("orderId", orderId);
                params.put("hasPrepared", order.hasPreparedDishes());
                params.put("isCancelled", order.isCancelled());
                params.put("isClosed", !order.isOpened());
                params.put("isFulfilled", order.isFulfilled());
                return new ResponseEntity(params, HttpStatus.OK);
        }

        @PostMapping(value="/waiter/edit_order")
        public ResponseEntity<Order> createOrUpdateOrder(@RequestBody Order order) {
                if (order.getDishes().isEmpty()) {
                        LOGGER.error("Error: "+NO_DISHES_MESSAGE);
                        return new ResponseEntity(NO_DISHES_MESSAGE,
                                HttpStatus.FORBIDDEN);
                }
                long startTime = System.currentTimeMillis();
                try {
                        orderService.processOrder(order);
                } catch (UnsupportedOperationException e) {
                        LOGGER.error("Error: "+ FORBIDDEN_ACTION_MESSAGE);
                        return new ResponseEntity(FORBIDDEN_ACTION_MESSAGE,
                                HttpStatus.FORBIDDEN);
                } catch (NotEnoughIngredientsException e) {
                        LOGGER.error("Error: "+INGREDIENTS_SHORTAGE_MESSAGE);
                        return new ResponseEntity(INGREDIENTS_SHORTAGE_MESSAGE,
                                HttpStatus.FORBIDDEN);
                } catch (Exception e) {
                        LOGGER.error("Error: "+e.getMessage()+
                                     "Cause: "+e.getCause()+ "Class: "+e.getClass());
                        return new ResponseEntity("Error: "+UNEXPECTED_ERROR_MESSAGE,
                                HttpStatus.FORBIDDEN);
                }
                long endTime   = System.currentTimeMillis();
                LOGGER.info("VALIDATION RUNTIME: "+(endTime - startTime)+" ms");
                LOGGER.info("Created/Edited order: "+order);
                return new ResponseEntity(order, HttpStatus.OK);
        }

        @GetMapping("/waiter/delete_order")
        public String deleteOrder(@RequestParam Long id, HttpServletRequest request, RedirectAttributes ra) {
                URL url;
                try {
                        url = new URL(request.getHeader("referer"));
                        orderService.deleteById(id);
                        LOGGER.info("Deleted Order #: "+id);
                } catch (Exception e) {
                        setErrorMessages(id, ra,
                                HttpStatus.FORBIDDEN.toString(),
                                e.getMessage(),
                                "Error: Failed to deleteById the Order #: " +id);
                        return "redirect:/error";
                }
                return "redirect:"+url.getPath();
        }

        @GetMapping("/waiter/close_order")
        public ResponseEntity closeOrder(@RequestParam Long id) {
                try {
                        orderService.closeOrder(id);
                } catch (Exception e) {
                        LOGGER.error("Failed to close Order# "+id);
                        return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
                }
                LOGGER.info("Closed Order #: "+id);
                return new ResponseEntity(HttpStatus.OK);
        }

        @GetMapping("/waiter/cancel_order")
        public ResponseEntity cancelOrder(@RequestParam Long id) {
                try {
                        orderService.cancelOrder(id);
                } catch (Exception e) {
                        LOGGER.error("Error: Failed to cancel the Order #: "+id);
                        return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
                }
                LOGGER.info("Cancelled Order #: "+id);
                return new ResponseEntity("{\"orderId\":" +id+"}", HttpStatus.OK);
        }

        private void setErrorMessages(Long id, RedirectAttributes ra, String... messages) {
                LOGGER.error("ERROR: status: "+messages[0]+" / error: "+messages[1]+"/ message: "+messages[2]);
                ra.addFlashAttribute("status", messages[0]);
                ra.addFlashAttribute("error", messages[1]);
                ra.addFlashAttribute("message", messages[2]);
        }
}
