package com.goit.popov.restaurant.controller.converters;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.DishServiceImpl;
import com.goit.popov.restaurant.service.EmployeeService;
import com.goit.popov.restaurant.service.OrderService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @see "http://www.baeldung.com/jackson-deserialization"
 * Created by Andrey on 06.12.2016.
 */
public class OrderDeserializer extends JsonDeserializer<Order> {

        private static Logger logger = (Logger) LoggerFactory.getLogger(OrderDeserializer.class);

        @Autowired
        private EmployeeService waiterService;

        @Autowired
        private DishServiceImpl dishService;

        @Autowired
        private OrderService orderService;

        @Override
        public Order deserialize(JsonParser p, DeserializationContext ctxt) {
                JsonNode node = null;
                try {
                        node = p.getCodec().readTree(p);
                } catch (IOException e) {
                        logger.info("Error while obtaining the node tree: "+e.getMessage());
                }
                Long id;
                boolean isOpened = false;
                String openedTS;
                Date openedTimeStamp = null;
                String closedTS;
                Date closedTimeStamp = null;
                Long waiterId;
                String tableInString;
                String table = "";
                ArrayNode dishes;
                Map<Dish, Integer> dishesInMap = null;
                Order order = null;
                try {
                        id = Long.parseLong(node.get("id").asText());
                        logger.info("OK. id= "+id);
                        table = node.get("table").asText();
                        dishes = ((ArrayNode) node.get("dishes"));
                        dishesInMap = convertJSONToMap(dishes);
                        logger.info("OK. map= "+dishesInMap.size());
                        if (id==0 ) {
                                String wid = node.get("waiter").asText();
                                waiterId = Long.parseLong(wid);
                                isOpened = true;
                                openedTimeStamp = convertStringToData(node.get("openedTimeStamp").asText());
                                order = createOrder(id, isOpened, openedTimeStamp, closedTimeStamp,
                                        waiterId, table, dishesInMap);
                        } else {
                                order = updateOrder(id, table, dishesInMap);
                        }
                } catch (ParseException e) {
                        logger.info("Error while parsing JSON: " + e.getMessage());
                        throw new RuntimeJsonMappingException("Parsing error");
                } catch (Exception e) {
                        logger.info("Error while processing JSON: " + e.getMessage());
                        throw new RuntimeJsonMappingException("Processing error");
                }
                return order;
        }

        private Order updateOrder(Long orderId, String table, Map<Dish, Integer> dishes) {
                Order order = orderService.getById(orderId);
                order.setTable(table);
                order.setPreviousDishes(order.getDishes());
                order.setDishes(dishes);
                return order;
        }

        private Order createOrder(Long id, boolean isOpened, Date openedTimeStamp, Date closedTimeStamp,
                                  Long waiterId, String table, Map<Dish, Integer> dishesInMap) {
                Order order = new Order();
                order.setId(id);
                order.setOpened(isOpened);
                order.setOpenedTimeStamp(openedTimeStamp);
                order.setClosedTimeStamp(closedTimeStamp);
                order.setTable(table);
                order.setWaiter(waiterService.getById(waiterId));
                order.setDishes(dishesInMap);
                return order;
        }

        private Date convertStringToData(String dateInString) throws ParseException {
                // Format for new Order
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                format.setTimeZone(TimeZone.getTimeZone("UTC"));
                //format.setTimeZone(TimeZone.getTimeZone("Europe/Budapest"));
                Date date = null;
                try {
                        date = format.parse(dateInString);
                } catch (ParseException e) {
                        // Format for existing Order
                        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                        date = format.parse(dateInString);
                }
                return date;
        }

        private Map<Dish, Integer> convertJSONToMap(ArrayNode arrayNode) {
                ObjectMapper mapper = new ObjectMapper();
                Map<Dish, Integer> map = new HashMap<>();
                try {
                        for (JsonNode jsonNode : arrayNode) {
                                Dish dish = dishService.getById(Long.parseLong(jsonNode.get("dishId").asText()));
                                Integer quantity = (Integer) jsonNode.get("quantity").numberValue();
                                if (quantity <= 0) throw new RuntimeException("Quantity cannot be negative!");
                                map.put(dish, quantity);
                        }
                } catch (Exception e) {
                        logger.error("Error: "+e.getMessage());
                }
                return map;
        }
}
