package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.goit.popov.restaurant.dao.PreparedDishHistoryDAO;
import com.goit.popov.restaurant.model.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Andrey on 1/15/2017.
 */
public class PreparedDishServiceImpl implements PreparedDishService {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(PreparedDishServiceImpl.class);

        @Autowired
        private PreparedDishHistoryDAO preparedDishDAO;

        @Autowired
        private OrderService orderService;

        @Autowired
        private DishServiceImpl dishService;

        @Autowired
        private EmployeeService chefService;

        @Autowired
        private Service service;

        @Autowired
        private StockService stockService;

        @Override
        public Long insert(PreparedDish preparedDish) {
                return preparedDishDAO.insert(preparedDish);
        }

        @Override
        public void update(PreparedDish preparedDish) {
                preparedDishDAO.update(preparedDish);
        }

        public List<PreparedDish> getAll() {
                return preparedDishDAO.getAll();
        }

        @Override
        public PreparedDish getById(Long id) {
                return preparedDishDAO.getById(id);
        }

        @Override
        public void delete(PreparedDish preparedDish) {
                preparedDishDAO.delete(preparedDish);
        }

        @Override
        public Long count() {
                return preparedDishDAO.count();
        }

        @Override
        public Long getPreparedDishesQuantity(Dish dish, Order order) {
                return preparedDishDAO.getPreparedDishesQuantity(dish, order);
        }

        @Override
        public Long getCancelledDishesQuantity(Dish dish, Order order) {
                return preparedDishDAO.getCancelledDishesQuantity(dish, order);
        }

        @Override
        public Long getPreparedDishesQuantity(Order order) {
                return preparedDishDAO.getPreparedDishesQuantity(order);
        }

        @Transactional
        @Override
        public void confirmDishesPrepared(Long dishId, Integer quantity, Long orderId, Long chefId) {
                Set<PreparedDish> preparedDishes = createPreparedDishes(dishId, quantity, orderId, chefId, false);
                savePreparedDishes(preparedDishes);
        }

        @Transactional
        @Override
        public void confirmDishesCancelled(Long dishId, Integer quantity, Long orderId, Long chefId) {
                Set<PreparedDish> preparedDishes = createPreparedDishes(dishId, quantity, orderId, chefId, true);
                savePreparedDishes(preparedDishes);
                returnIngredients(dishId, quantity);
        }

        private void savePreparedDishes(Set<PreparedDish> preparedDishes) {
                for (PreparedDish preparedDish : preparedDishes) {
                        insert(preparedDish);
                }
        }

        private void returnIngredients(Long dishId, Integer quantity) {
                Dish dish = dishService.getById(dishId);
                Map<Dish, Integer> dishes = new HashMap<>();
                dishes.put(dish, quantity);
                stockService.increaseIngredients(service.getIngredients(dishes));
        }

        private Set<PreparedDish> createPreparedDishes(Long dishId, Integer quantity, Long orderId, Long chefId, boolean isCancelled) {
                Dish dish = dishService.getById(dishId);
                Order order = orderService.getById(orderId);
                Employee chef = chefService.getById(chefId);
                Set<PreparedDish> preparedDishes = new HashSet<>();
                for (int i=0; i < quantity; i++) {
                        PreparedDish preparedDish = new PreparedDish();
                        preparedDish.setChef(chef);
                        preparedDish.setDish(dish);
                        preparedDish.setOrder(order);
                        preparedDish.setWhenPrepared(new Date());
                        preparedDish.setCancelled(isCancelled);
                        preparedDishes.add(preparedDish);
                }
                return preparedDishes;
        }

        @Override
        public ArrayNode toJSON(List<Order> orders) {
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode ana = mapper.createArrayNode();
                for (Order order : orders) {
                        ObjectNode a = mapper.createObjectNode();
                        a.put("id", order.getId());
                        a.put("waiter", order.getWaiter().getName());
                        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm");
                        a.put("openedTimeStamp", sdfDate.format(order.getOpenedTimeStamp()));
                        a.put("dishes", order.getDishesQuantity());
                        a.put("isFulfilled", order.isFulfilled());
                        a.put("isCancelled", order.isCancelled());
                        ana.add(a);
                }
                return ana;
        }

        @Override
        public ArrayNode toJSON(Order order) {
                Map<Dish, Integer> dishes = order.getDishes();
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode ana = mapper.createArrayNode();
                for (Map.Entry<Dish, Integer> dish : dishes.entrySet()) {
                        ObjectNode a = mapper.createObjectNode();
                        Dish nextDish = dish.getKey();
                        a.put("id", nextDish.getId());
                        a.put("dish", nextDish.getName());
                        a.put("quantity", dish.getValue());
                        a.put("isPrepared", isPrepared(nextDish, order));
                        // Decide if the Dish was rejected from getting prepared and ingredients returned to Stock
                        a.put("isReturned", isCancelled(nextDish, order));
                        ana.add(a);
                }
                return ana;
        }

        private boolean isCancelled(Dish dish, Order order) {
                if (getCancelledDishesQuantity(dish, order)!=0) return true;
                return false;
        }

        private boolean isPrepared(Dish dish, Order order) {
                return order.getDishesQuantity(dish)==getPreparedDishesQuantity(dish, order);
        }
}
