package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.goit.popov.restaurant.dao.entity.PreparedDishHistoryDAO;
import com.goit.popov.restaurant.model.*;
import com.goit.popov.restaurant.service.dataTables.DataTablesListToJSONConvertible;
import com.goit.popov.restaurant.service.dataTables.DataTablesObjectToJSONConvertible;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 1/15/2017.
 */
public class PreparedDishService implements DataTablesListToJSONConvertible<Order>, DataTablesObjectToJSONConvertible<Order> {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(PreparedDishService.class);

        @Autowired
        private PreparedDishHistoryDAO preparedDishDAO;

        public List<PreparedDish> getAll() {
                return preparedDishDAO.getAll();
        }

        public List<PreparedDish> getAllChefToday(int chefId) {
                return preparedDishDAO.getAllChefToday(chefId);
        }

        public List<Order> getAllOrdersForChef() {
                return preparedDishDAO.getAllOrderForChef();
        }

        @Override
        public ArrayNode toJSON(List<Order> orders) {
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode ana = mapper.createArrayNode();
                for (Order order : orders) {
                        ObjectNode a = mapper.createObjectNode();
                        a.put("id", order.getId());
                        a.put("waiter", order.getWaiterName());
                        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        a.put("openedTimeStamp", sdfDate.format(order.getOpenedTimeStamp()));
                        a.put("dishes", order.getDishesQuantity());
                        a.put("isFulfilled", isFulfilled(order));
                        ana.add(a);
                }
                return ana;
        }

        private boolean isFulfilled(Order order) {
                logger.info("Order #: "+order.getId());
                logger.info("Expected quantity: "+order.getDishesQuantity());
                logger.info("Actual quantity: "+preparedDishDAO.getPreparedDishesQuantity(order));
                return order.getDishesQuantity()==preparedDishDAO.getPreparedDishesQuantity(order);
        }

        @Override
        public ArrayNode toJSON(Order order) {
                Map<Dish, Integer> dishes = order.getDishes();
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode ana = mapper.createArrayNode();
                for (Map.Entry<Dish, Integer> dish : dishes.entrySet()) {
                        ObjectNode a = mapper.createObjectNode();
                        a.put("id", dish.getKey().getId());
                        a.put("dish", dish.getKey().getName());
                        a.put("quantity", dish.getValue());
                        a.put("isPrepared", isPrepared(dish.getKey(), order));
                        ana.add(a);
                }
                return ana;
        }

        private boolean isPrepared(Dish dish, Order order) {
                logger.info("Order #: "+order.getId()+ "/ Dish #: "+dish.getId());
                logger.info("Expected quantity: "+order.getDishesQuantity(dish));
                logger.info("Actual quantity: "+preparedDishDAO.getPreparedDishesQuantity(dish, order));
                return order.getDishesQuantity(dish)==preparedDishDAO.getPreparedDishesQuantity(dish, order);
        }

}
