package com.goit.popov.restaurant.controller.converters;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.DishService;
import com.goit.popov.restaurant.service.WaiterService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Andrey on 06.12.2016.
 */
public class OrderDeserializer extends JsonDeserializer<Order> {

        static Logger logger = (Logger) LoggerFactory.getLogger(OrderDeserializer.class);

        @Autowired
        private WaiterService Waiter;

        @Autowired
        private DishService dishService;

        /**
         * @See http://www.baeldung.com/jackson-deserialization
         * @param p
         * @param ctxt
         * @return
         */
        @Override
        public Order deserialize(JsonParser p, DeserializationContext ctxt) throws JsonMappingException {
                JsonNode node = null;
                try {
                        node = p.getCodec().readTree(p);
                } catch (IOException e) {
                        logger.info("Error while obtaining the node tree: "+e.getMessage());
                }
                int id = 0;
                boolean isOpened = false;
                String openedTS;
                Date openedTimeStamp = null;
                String closedTS;
                Date closedTimeStamp = null;
                int waiterId = 0;
                String tableInString;
                int table = 0;
                ArrayNode dishes;
                Map<Dish, Integer> dishesInMap = null;
                try {
                        id = (Integer) ((IntNode) node.get("id")).numberValue();
                        isOpened = node.get("isOpened").booleanValue();
                        openedTS = node.get("openedTimeStamp").asText();
                        openedTimeStamp = convertStringToData(openedTS);
                        if (!node.get("closedTimeStamp").isNull()) {
                                closedTS = node.get("closedTimeStamp").asText();
                                closedTimeStamp = convertStringToData(closedTS);
                        }
                        tableInString = node.get("table").asText();
                        table = Integer.parseInt(tableInString);
                        waiterId = (Integer) ((IntNode) node.get("waiter")).numberValue();
                        dishes = ((ArrayNode) node.get("dishes"));
                        dishesInMap = convertJSONToMap(dishes);
                } catch (ParseException e) {
                        logger.info("Error while parsing JSON: " + e.getMessage());
                        throw new RuntimeJsonMappingException("Parsing error");
                } catch (Exception e) {
                        logger.info("Error while processing JSON: " + e.getMessage());
                        throw new RuntimeJsonMappingException("Processing error");
                }
                Order order = getOrder(id, isOpened, openedTimeStamp, closedTimeStamp, waiterId, table, dishesInMap);
                return order;
        }

        /**
         * Creates an Order object based on params from JSON file
         * @param id
         * @param isOpened
         * @param openedTimeStamp
         * @param closedTimeStamp
         * @param waiterId
         * @param table
         * @param dishesInMap
         * @return
         */
        private Order getOrder(int id, boolean isOpened, Date openedTimeStamp, Date closedTimeStamp,
                               int waiterId, int table, Map<Dish, Integer> dishesInMap) {
                Order order = new Order();
                order.setId(id);
                order.setOpened(isOpened);
                order.setOpenedTimeStamp(openedTimeStamp);
                order.setClosedTimeStamp(closedTimeStamp);
                order.setTable(table);
                order.setWaiter(Waiter.getEmployeeById(waiterId));
                order.setDishes(dishesInMap);
                return order;
        }

        /**
         * Converts Date in String to Java Date format
         * @param dateInString
         * @return
         * @throws ParseException
         */
        private Date convertStringToData(String dateInString) throws ParseException {
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
                format.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date date = format.parse(dateInString);
                return date;
        }

        /**
         * Converts ArrayNode (which in fact is JSON string representing array of objects to Map<Dish, Integer>
         * @param arrayNode
         * @return
         */
        private Map<Dish, Integer> convertJSONToMap(ArrayNode arrayNode) {
                ObjectMapper mapper = new ObjectMapper();
                Map<Dish, Integer> map = new HashMap<>();
                try {
                        for (JsonNode jsonNode : arrayNode) {
                                Dish dish = dishService.getById((Integer) jsonNode.get("dishId").numberValue()); ;
                                Integer quantity = (Integer) jsonNode.get("quantity").numberValue();
                                map.put(dish, quantity);
                        }
                } catch (Exception e) {
                        logger.error("Error: "+e.getMessage());
                }
                return map;
        }
}
