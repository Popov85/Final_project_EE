package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.goit.popov.restaurant.dao.entity.OrderDAO;
import com.goit.popov.restaurant.model.*;
import com.goit.popov.restaurant.service.dataTables.*;
import com.goit.popov.restaurant.service.exceptions.NotEnoughIngredientsException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 12/3/2016.
 */
public class OrderServiceImpl implements OrderService {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(OrderServiceImpl.class);

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

        public List<Order> getAllOpened() {
                return orderDAO.getAllOpened();
        }

        @Override
        public List<Order> getAllWaiterToday(int waiterId) {
                return orderDAO.getAllWaiterToday(waiterId);
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

        public long count() {
                return orderDAO.count();
        }

        @Override
        public long countWaiter(Waiter waiter) {
                return orderDAO.countWaiter(waiter);
        }

        @Override
        public List<Order> getAllOrders(DataTablesInputExtendedDTO dt) {
                return orderDAO.getAllOrders(dt);
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
         * 1. Get Map copy of Stock state
         * 2. Get all opened Orders
         * 3. Add them to the list
         * 4. For each OPENED and NOT FULFILLED Order get map of dishes
         * 5. Take into account partially done Orders
         * 6. For each NOT PREPARED order's dish get map of ingredients
         * 7. Detract the total value of ingredient required from stock
         *    if we get negative quantity somewhere - return false
         *    at the end return true
         * @param order Order to be validated
         * @return true if there is enough ingredients in stock to fulfill the Order,
         *         false - otherwise
         */
        @Override
        public boolean validateOrder(Order order) {
                Map<Ingredient, Double> stock = stockService.convertStockToMap(stockService.getAll());
                List<Order> orders = getAllOpened();
                if (orders.contains(order)) {
                        logger.info("Contains: "+order);
                        orders.set(orders.indexOf(order),order);
                } else {
                        logger.info("Does not contain: "+order);
                        orders.add(order);
                }
                logger.info("Current stock:");
                stockService.toStringStock(stock);
                if (!validateOrders(orders, stock)) return false;
                logger.info("Updated stock:");
                stockService.toStringStock(stock);
                return true;
        }

        private boolean validateOrders(List<Order> orders, Map<Ingredient, Double> stock) {
                for (Order order : orders) {
                        if (!order.isFulfilled()) {
                                Map<Dish, Integer> dishes;
                                if (!order.hasPreparedDishes()) {
                                        dishes = order.getDishes();
                                } else {
                                        dishes = order.getNotPreparedDishes();
                                }
                                if (!validateDishes(stock, dishes)) return false;
                        }
                }
                return true;
        }

        private boolean validateDishes(Map<Ingredient, Double> stock, Map<Dish, Integer> dishes) {
                for (Map.Entry<Dish, Integer> entry : dishes.entrySet()) {
                        Dish nextDish = entry.getKey();
                        Integer nextQuantity = entry.getValue();
                        Map<Ingredient, Double> ingredients = nextDish.getIngredients();
                        for (Map.Entry<Ingredient, Double> ingredient : ingredients.entrySet()) {
                                if (!validateIngredients(stock, ingredient, nextQuantity)) return false;
                        }
                }
                return true;
        }

        private boolean validateIngredients(Map<Ingredient, Double> stock,
                                            Map.Entry<Ingredient, Double> ingredient, Integer nextQuantity) {
                Double stockQuantity = stock.get(ingredient.getKey()).doubleValue();
                Double requiredQuantity = ingredient.getValue() * nextQuantity;
                if (stockQuantity - requiredQuantity < 0) return false;
                stock.put(ingredient.getKey(), (stockQuantity - requiredQuantity));
                return true;
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
