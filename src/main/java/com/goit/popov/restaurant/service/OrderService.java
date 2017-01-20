package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.goit.popov.restaurant.dao.entity.OrderDAO;
import com.goit.popov.restaurant.dao.entity.StoreHouseDAO;
import com.goit.popov.restaurant.model.*;
import com.goit.popov.restaurant.service.dataTables.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 12/3/2016.
 */
public class OrderService implements OrderServiceInterface,
        DataTablesMapToJSONConvertible<Dish, Integer>,  DataTablesSearchableParam<Order> {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(OrderService.class);

        @Autowired
        private OrderDAO orderDAO;

        @Autowired
        private DishService dishService;

        @Autowired
        private StoreHouseDAO storeHouseDAO;

        @Autowired
        private PreparedDishService preparedDishService;

        @Autowired
        private StockService stockService;

        public List<Order> getAll() {
                return orderDAO.getAll();
        }

        public void insert(Order order) {
                orderDAO.insert(order);
        }

        public void update(Order order) {
                orderDAO.update(order);
        }

        public Order getById(int id) {
                return orderDAO.getById(id);
        }

        public void delete(Order order) {
                orderDAO.delete(order);
        }

        public void delete(int orderId) {
                orderDAO.delete(orderId);
        }

        public void close(Order order) {
                orderDAO.close(order);
        }

        public List<Order> getAllClosed() {
                return orderDAO.getAllClosed();
        }

        public List<Order> getAllOpened() {
                return orderDAO.getAllOpened();
        }

        public List<Order> getAllWaiterToday(int waiterId) {
                return orderDAO.getAllWaiterToday(waiterId);
        }

        public List<Order> getAllToday() {
                return orderDAO.getAllToday();
        }

        public List<PreparedDish> getAllWithPreparedDishes() {
                return orderDAO.getAllWithPreparedDishes();
        }

        public Integer[] getTables() {
                return Order.TABLE_SET;
        }

        @Transactional
        @Override
        public void closeOrder(int orderId) {
                Order order = getById(orderId);
                order.setOpened(false);
                order.setClosedTimeStamp(new Date());
                update(order);
        }

        @Transactional
        @Override
        public long count() {
                return orderDAO.count();
        }

        public long countWaiter(Waiter waiter) {
                return orderDAO.countWaiter(waiter);
        }

        @Transactional
        @Override
        public Map<Dish, Integer> getDishes(int orderId) {
                Order order = orderDAO.getById(orderId);
                return order.getDishes();
        }


        // TODO include orders that have not ben fulfilled yet.
        @Deprecated
        @Transactional
        @Override
        public boolean validateIngredients(Map<Dish, Integer> dishes) {
                for (Map.Entry<Dish, Integer> entry : dishes.entrySet()) {
                        Dish dish = entry.getKey();
                        Integer quantityOrdered = entry.getValue();
                        if (!this.dishService.validateIngredients(dish.getId(), quantityOrdered)) {
                                return false;
                        }
                }
                return true;
        }

        @Transactional
        public boolean validateOrder(Order o) {

                List<Order> orders = new ArrayList<>();
                orders.add(o);
                // 0. Get Map of Stock state
                Map<Ingredient, Double> stock = stockService.convertStockToMap(stockService.getAll());
                System.out.println("Current stock:");
                stockService.printStockState(stock);
                // 1. Get all opened orders
                // 2. Add them to the list
                orders.addAll(1, getAllOpened());
                System.out.println("orders size: "+orders.size());
                // Get all opened orders
                for (Order order : orders) {
                        // For each OPENED (NOT FULFILLED !!!) Order get map of dishes
                        if (!order.isFulfilled()) {
                                // 1. 0 preparedDishes
                                // 2. Some preparedDishes
                                Map<Dish, Integer> dishes;
                                if (!order.hasPreparedDishes()) {
                                        dishes = order.getDishes();
                                } else {
                                        dishes = order.getNotPreparedDishes();
                                }
                                // For each NOT PREPARED order's dish get map of ingredients
                                if (!validateDishes(stock, dishes)) return false;
                        }
                }
                System.out.println("Updated stock:");
                stockService.printStockState(stock);
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
                // Detract the total value of ingredient required from stock
                // if we get negative quantity - return false
                // at the end return true
                if (stockQuantity - requiredQuantity < 0) {
                        return false;
                }
                stock.put(ingredient.getKey(), (stockQuantity - requiredQuantity));
                return true;
        }

        // TODO it is done in another place
        @Deprecated
        @Transactional
        @Override
        public void updateStock(Order order) {
                Map<Dish, Integer> dishes = order.getDishes();
                for (Map.Entry<Dish, Integer> entry : dishes.entrySet()) {
                        Dish dish = entry.getKey();
                        Integer quantityOrdered = entry.getValue();
                        Map<Ingredient, Double> ingredients = dish.getIngredients();
                        for (Map.Entry<Ingredient, Double> ing : ingredients.entrySet()) {
                                Ingredient ingredient = ing.getKey();
                                Double quantityRequired = ing.getValue();
                                stockService.decreaseQuantity(ingredient, quantityRequired * quantityOrdered);
                        }
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
                List<Order> data = orderDAO.getAll(dt);
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


        public DataTablesOutputDTOUniversal<Order> getAllWaiterArchive(DataTablesInputExtendedDTO dt, int waiterId) {
                return getAll(dt, waiterId);
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
}
