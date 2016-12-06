package com.goit.popov.restaurant.controller.converters;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.OrderService;
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
        private OrderService orderService;

        @Autowired
        private WaiterService Waiter;

        /**
         * @See http://www.baeldung.com/jackson-deserialization
         * @param p
         * @param ctxt
         * @return
         */
        @Override
        public Order deserialize(JsonParser p, DeserializationContext ctxt) {
                JsonNode node = null;
                try {
                        node = p.getCodec().readTree(p);
                        logger.info("Node: "+node);
                } catch (IOException e) {
                        logger.info("Error: "+e.getMessage());
                }
                int id = 0;
                boolean isOpened = false;
                String openedTS = null;
                Date openedTimeStamp = null;
                String closedTS = null;
                Date closedTimeStamp = null;
                int waiterId = 0;
                String tableInString = null;
                int table = 0;
                String dishes;
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
                        dishes = ((ArrayNode) node.get("dishes")).toString();
                        logger.info("dishes (JSON object array): "+dishes);
                        convertJSONToMap(dishes);
                } catch (ParseException e) {
                        logger.info("Error while parsing JSON Date: "+e.getMessage());
                } catch (Exception e) {
                        logger.info("Error while parsing JSON: "+e.getMessage());
                }
                Order order = new Order();
                order.setId(id);
                order.setOpened(isOpened);
                order.setOpenedTimeStamp(openedTimeStamp);
                order.setClosedTimeStamp(closedTimeStamp);
                order.setTable(table);
                order.setWaiter(Waiter.getEmployeeById(waiterId));

                return order;
        }

        private Date convertStringToData(String openedTS) throws ParseException {
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
                format.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date date = format.parse(openedTS);
                return date;
        }

        private Map<String, Object> convertJSONToMap(String jsonObjectArray) {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> map = new HashMap<String, Object>();
                // convert JSON string to Map
                try {
                        map = mapper.readValue(jsonObjectArray, new TypeReference<Map<String, String>>(){});
                } catch (IOException e) {
                        logger.info("Error: "+e.getMessage());
                }
                logger.info("Map: "+map);
                return map;
        }
}
