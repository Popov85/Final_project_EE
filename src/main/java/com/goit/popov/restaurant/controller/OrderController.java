package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.OrderService;
import com.goit.popov.restaurant.service.dataTables.*;
import com.goit.popov.restaurant.service.DishServiceImpl;
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
import java.util.List;

/**
 * Created by Andrey on 02.12.2016.
 */
@Controller
public class OrderController {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(OrderController.class);

        private static final String NO_DISHES_MESSAGE = "Order must contain dishes!";
        private static final String INGREDIENTS_SHORTAGE_MESSAGE = "Not enough ingredients to fulfill the order!";
        private static final String FORBIDDEN_ACTION_MESSAGE = "This action is forbidden!";
        private static final String UNEXPECTED_ERROR_MESSAGE ="Unexpected error happened";

        @Autowired
        private DishServiceImpl dishService;

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
        public DataTablesOutputDTOCollectionWrapper getOrdersDishes(@RequestParam Integer orderId) {
                DataTablesOutputDTOCollectionWrapper data = new DataTablesOutputDTOCollectionWrapper();
                data.setData(orderService.toJSON(orderService.getById(orderId).getDishes()));
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
        public String getOrdersTable(@RequestParam Integer orderId) {
                String table = orderService.getById(orderId).getTable();
                return table;
        }

        // Create (Page)
        @GetMapping("/waiter/new_order")
        public String showOrderForm() {
                return "th/new_order";
        }

        // Read All (Page) Manager view
        @GetMapping(value = "/admin/orders")
        public String showOrdersTableManager() {
                return "th/manager/orders";
        }

        // Read All (Page) Waiter view (today)
        @GetMapping(value = "/waiter/orders/today")
        public String showOrdersTableWaiter() {
                return "th/waiter/orders_today";
        }

        // Read All (Page) Waiter view (archive)
        @GetMapping(value = "/waiter/orders/archive")
        public String showOrdersTableWaiterArchive() {
                return "th/waiter/archive/orders_archive";
        }

        @PostMapping(value = "/get_orders")
        @ResponseBody
        public DataTablesOutputDTOUniversal<Order> getOrders(DataTablesInputExtendedDTO input) {
                DataTablesOutputDTOUniversal<Order> data = orderService.getAllOrders(input);
                return data;
        }

        @PostMapping(value = "/get_order")
        @ResponseBody
        public Order getOrder(@RequestParam int orderId) {
                logger.info("Order: "+orderService.getById(orderId));
                return orderService.getById(orderId);
        }

        @PostMapping(value = "/waiter/get_orders")
        @ResponseBody
        public DataTablesOutputDTOListWrapper<Order> getWaiterOrders(@RequestParam int waiterId) {
                DataTablesOutputDTOListWrapper<Order> data = new DataTablesOutputDTOListWrapper<>();
                        data.setData(orderService.getAllWaiterToday(waiterId));
                return data;
        }

        @PostMapping(value = "/waiter/get_archive")
        @ResponseBody
        public DataTablesOutputDTOUniversal<Order> getWaiterOrdersArchive(@RequestParam int waiterId,
                                                                          DataTablesInputExtendedDTO input) throws JsonProcessingException {
                DataTablesOutputDTOUniversal<Order> data = orderService.getAllOrdersByWaiter(input, waiterId);
                return data;
        }

        // Update (Page)
        @GetMapping("/edit_order")
        public ModelAndView editOrder(@RequestParam int id) {
                ModelAndView modelAndView = new ModelAndView("th/edit_order");
                modelAndView.addObject("id", id);
                return modelAndView;
        }

        @PostMapping("/waiter/check_order")
        public ResponseEntity checkOrder(@RequestParam int orderId) {
                Order order = orderService.getById(orderId);
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode node = mapper.createObjectNode();
                node.put("orderId", orderId);
                node.put("hasPrepared", order.hasPreparedDishes());
                node.put("isCancelled", order.isCancelled());
                node.put("isClosed", !order.isOpened());
                node.put("isFulfilled", order.isFulfilled());
                return new ResponseEntity(node, HttpStatus.OK);
        }

        // Create Or Update (Action)
        @PostMapping(value="/edit_order")
        public ResponseEntity createOrUpdateOrder(@RequestBody Order order) {
                logger.info("Create/Edit order #: "+order.getId());
                if (order.getDishes().isEmpty()) {
                        logger.error("Error: "+NO_DISHES_MESSAGE);
                        return new ResponseEntity(NO_DISHES_MESSAGE,
                                HttpStatus.FORBIDDEN);
                }
                long startTime = System.currentTimeMillis();
                try {
                        orderService.processOrder(order);
                } catch (UnsupportedOperationException e) {
                        logger.error("Error: "+ FORBIDDEN_ACTION_MESSAGE);
                        return new ResponseEntity(FORBIDDEN_ACTION_MESSAGE,
                                HttpStatus.FORBIDDEN);
                } catch (NotEnoughIngredientsException e) {
                        logger.error("Error: "+INGREDIENTS_SHORTAGE_MESSAGE);
                        return new ResponseEntity(INGREDIENTS_SHORTAGE_MESSAGE,
                                HttpStatus.FORBIDDEN);
                } catch (Exception e) {
                        logger.error("Error: "+e.getMessage()+
                                     "Cause: "+e.getCause()+ "Class: "+e.getClass());
                        return new ResponseEntity("Error: "+UNEXPECTED_ERROR_MESSAGE,
                                HttpStatus.FORBIDDEN);
                }
                long endTime   = System.currentTimeMillis();
                logger.info("VALIDATION RUNTIME: "+(endTime - startTime)+" ms");
                return new ResponseEntity("{\"Result\": \"Success\"}", HttpStatus.OK);
        }

        // Delete (Action)
        @GetMapping("/delete_order")
        public String deleteOrder(@RequestParam int id, HttpServletRequest request, RedirectAttributes ra) {
                URL url;
                try {
                        url = new URL(request.getHeader("referer"));
                        orderService.delete(id);
                        logger.info("Deleted Order #: "+id);
                } catch (Exception e) {
                        setErrorMessages(id, ra,
                                HttpStatus.FORBIDDEN.toString(),
                                e.getMessage(),
                                "Error: Failed to delete the Order #: " +id);
                        return "redirect:/error";
                }
                return "redirect:"+url.getPath();
        }

        // Close (Action)
        @GetMapping("/close_order")
        public String closeOrder(@RequestParam int id, HttpServletRequest request, RedirectAttributes ra) {
                URL url=null;
                try {
                        url = new URL(request.getHeader("referer"));
                        orderService.closeOrder(id);
                        logger.info("Closed Order #: "+id);
                } catch (Exception e) {
                        setErrorMessages(id, ra,
                                HttpStatus.FORBIDDEN.toString(),
                                FORBIDDEN_ACTION_MESSAGE,
                                "Error: Failed to close the Order #: "+id);
                        return "redirect:/error";
                }
                return "redirect:"+url.getPath();
        }

        // Cancel (Action)
        @GetMapping("/cancel_order")
        public String cancelOrder(@RequestParam int id, HttpServletRequest request, RedirectAttributes ra) {
                URL url=null;
                try {
                        url = new URL(request.getHeader("referer"));
                        orderService.cancelOrder(id);
                        logger.info("Cancelled Order #: "+id);
                } catch (Exception e) {
                        setErrorMessages(id, ra,
                                HttpStatus.FORBIDDEN.toString(),
                                FORBIDDEN_ACTION_MESSAGE,
                                "Error: Failed to cancel the Order #: "+id);
                        return "redirect:/error";
                }
                return "redirect:"+url.getPath();
        }

        private void setErrorMessages(int id, RedirectAttributes ra, String... messages) {
                logger.error("ERROR: status: "+messages[0]+" / error: "+messages[1]+"/ message: "+messages[2]);
                ra.addFlashAttribute("status", messages[0]);
                ra.addFlashAttribute("error", messages[1]);
                ra.addFlashAttribute("message", messages[2]);
        }
}
