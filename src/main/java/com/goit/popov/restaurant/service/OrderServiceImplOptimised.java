package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.goit.popov.restaurant.dao.entity.OrderDAO;
import com.goit.popov.restaurant.model.*;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOUniversal;
import com.goit.popov.restaurant.service.exceptions.NotEnoughIngredientsException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 23.01.2017.
 */

public class OrderServiceImplOptimised implements OrderService {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(OrderServiceImplOptimised.class);

        @Autowired
        private OrderDAO orderDAO;

        @Autowired
        private StockService stockService;

        @Override
        public List<Order> getAll() {
                return orderDAO.getAll();
        }

        @Override
        public int insert(Order order) {
                return orderDAO.insert(order);
        }

        @Override
        public void update(Order order) {
                orderDAO.update(order);
        }

        @Override
        public Order getById(int id) {
                return orderDAO.getById(id);
        }

        @Override
        public void delete(Order order) {
                orderDAO.delete(order);
        }

        @Override
        public void delete(int orderId) {
                orderDAO.delete(orderId);
        }

        @Override
        public void close(Order order) {
                orderDAO.close(order);
        }

        @Override
        public List<Order> getAllClosed() {
                return orderDAO.getAllClosed();
        }

        @Override
        public List<Order> getAllOpened() {
                return orderDAO.getAllOpened();
        }

        @Override
        public List<Order> getAllWaiterToday(int waiterId) {
                return orderDAO.getAllWaiterToday(waiterId);
        }

        @Override
        public List<Order> getAllOrders(DataTablesInputExtendedDTO dt) {
                return orderDAO.getAllOrders(dt);
        }

        @Override
        public List<Order> getAllWaiterArchive(int waiterId, DataTablesInputExtendedDTO dt) {
                return orderDAO.getAllWaiterArchive(waiterId, dt);
        }

        @Override
        public List<Order> getAllToday() {
                return orderDAO.getAllToday();
        }

        @Override
        public List<PreparedDish> getAllWithPreparedDishes() {
                return orderDAO.getAllWithPreparedDishes();
        }

        @Override
        public Integer[] getTables() {
                return Order.TABLE_SET;
        }

        @Override
        public long count() {
                return orderDAO.count();
        }

        @Override
        public long countWaiter(Waiter waiter) {
                return orderDAO.countWaiter(waiter);
        }

        @Override
        public void closeOrder(int orderId) {
                Order order = getById(orderId);
                order.setOpened(false);
                order.setClosedTimeStamp(new Date());
                update(order);
        }

        /**
         * Algorithm:
         * 1. Create a Map<Ingredient, Double> of ingredients needed to fulfill the opened orders
         *      including the current;
         * For this:
         *      a. Get all opened Orders;
         *      b. Add them to the list;
         *      c. For each OPENED and NOT FULFILLED Order get map of dishes;
         *      d. Take into account partially done Orders;
         *      e. For each NOT PREPARED order's dish get map of ingredients and put it in the resulting map
         *              taking into account their quantity;
         * 2. For each Ingredient get its quantity from the DB;
         * 3. Compare the quantity with the corresponding value in the map;
         *      If the value in the map is greater - return false,
         *      at the end - return true.
         * @param order Order to be validated
         * @return true if there is enough ingredients in stock to fulfill the Order,
         *         false - otherwise
         */
        @Override
        public boolean validateOrder(Order order) {
                // 1.
                List<Order> orders = getAllOpened();
                if (orders.contains(order)) {
                        orders.set(orders.indexOf(order),order);
                } else {
                        orders.add(order);
                }
                Map<Ingredient, Double> pendingIngredients = new HashMap<>();
                processOrders(orders, pendingIngredients);
                // 2-3.
                if (compareStock(pendingIngredients)) return true;
                return false;
        }

        private void processOrders(List<Order> orders, Map<Ingredient, Double> pendingIngredients) {
                for (Order order : orders) {
                        if (!order.isFulfilled()) {
                                Map<Dish, Integer> dishes;
                                if (!order.hasPreparedDishes()) {
                                        dishes = order.getDishes();
                                } else {
                                        dishes = order.getNotPreparedDishes();
                                }
                                processDishes(dishes, pendingIngredients);
                        }
                }
        }

        private boolean compareStock(Map<Ingredient, Double> pendingIngredients) {
                for (Map.Entry<Ingredient, Double> ingredient : pendingIngredients.entrySet()) {
                        Double stockQuantity = stockService.getQuantityByIngredient(ingredient.getKey());
                        Double pendingQuantity = ingredient.getValue();
                        logger.info("Ingredient in Stock: "+ ingredient.getKey().getName()+
                                "Stock: "+stockQuantity+
                                " / Required: "+pendingQuantity);
                        if (stockQuantity < pendingQuantity)
                                return false;
                }
                return true;
        }

        private void processDishes(Map<Dish, Integer> dishes, Map<Ingredient, Double> pendingIngredients) {
                for (Map.Entry<Dish, Integer> entry : dishes.entrySet()) {
                        Dish nextDish = entry.getKey();
                        Integer nextQuantity = entry.getValue();
                        Map<Ingredient, Double> ingredients = nextDish.getIngredients();
                        for (Map.Entry<Ingredient, Double> ingredient : ingredients.entrySet()) {
                                processIngredients(ingredient, nextQuantity, pendingIngredients);
                        }
                }
        }

        private void processIngredients(Map.Entry<Ingredient, Double> ingredient, Integer nextQuantity,
                                        Map<Ingredient, Double> pendingIngredients) {
                Double requiredQuantity = ingredient.getValue() * nextQuantity;
                if (pendingIngredients.containsKey(ingredient.getKey())) {
                        Double oldValue = pendingIngredients.get(ingredient.getKey()).doubleValue();
                        Double newValue = requiredQuantity+oldValue;
                        pendingIngredients.put(ingredient.getKey(), newValue);
                } else {
                        pendingIngredients.put(ingredient.getKey(), requiredQuantity);
                }
        }

        @Override
        public ArrayNode toJSON(Map<Dish, Integer> dishes) {
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode ana = mapper.createArrayNode();
                for (Map.Entry<Dish, Integer> dish : dishes.entrySet()) {
                        ObjectNode a = mapper.createObjectNode();
                        a.put("id", dish.getKey().getId());
                        a.put("name", dish.getKey().getName());
                        a.put("price", dish.getKey().getPrice());
                        a.put("quantity", dish.getValue());
                        ana.add(a);
                }
                return ana;
        }

        @Override
        public DataTablesOutputDTOUniversal<Order> getAll(DataTablesInputExtendedDTO dt) {
                long recordsTotal = count();
                long recordsFiltered;
                List<Order> data = orderDAO.getAllOrders(dt);
                if (!dt.getColumnSearch().isEmpty()) {
                        recordsFiltered = data.size();
                } else {
                        recordsFiltered=recordsTotal;
                }
                return new DataTablesOutputDTOUniversal<Order>()
                        .setDraw(dt.getDraw())
                        .setRecordsTotal(recordsTotal)
                        .setRecordsFiltered(recordsFiltered)
                        .setData(data);
        }

        @Override
        public DataTablesOutputDTOUniversal<Order> getAll(DataTablesInputExtendedDTO dt, int waiterId) {
                Waiter waiter = new Waiter();
                waiter.setId(waiterId);
                long recordsTotal = countWaiter(waiter);
                long recordsFiltered;
                List<Order> data = orderDAO.getAllWaiterArchive(waiterId, dt);
                if (!dt.getColumnSearch().isEmpty()) {
                        recordsFiltered = data.size();
                } else {
                        recordsFiltered=recordsTotal;
                }
                return new DataTablesOutputDTOUniversal<Order>()
                        .setDraw(dt.getDraw())
                        .setRecordsTotal(recordsTotal)
                        .setRecordsFiltered(recordsFiltered)
                        .setData(data);
        }

        @Override
        public void processOrder(Order order) throws NotEnoughIngredientsException {

        }
}
