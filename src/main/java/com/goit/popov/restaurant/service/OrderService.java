package com.goit.popov.restaurant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.goit.popov.restaurant.dao.entity.OrderDAO;
import com.goit.popov.restaurant.dao.entity.StoreHouseDAO;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 12/3/2016.
 */
public class OrderService implements OrderServiceInterface {

        @Autowired
        private OrderDAO orderDAO;

        @Autowired
        private DishService dishService;

        @Autowired
        private StoreHouseDAO storeHouseDAO;

        public void insert(Order order) {
                orderDAO.insert(order);
        }

        public void update(Order order) {
                orderDAO.update(order);
        }

        public List<Order> getAll() {
                return orderDAO.getAll();
        }

        public Order getById(int id) {
                return orderDAO.getById(id);
        }

        public void delete(Order order) {
                orderDAO.delete(order);
        }

        public void addDish(Order order, Dish dish, int quantity) {
                orderDAO.addDish(order, dish, quantity);
        }

        public void deleteDish(Order order, Dish dish, int quantity) {
                orderDAO.deleteDish(order, dish, quantity);
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

        @Transactional
        @Override
        public void closeOrder(int orderId) {
                Order order = getById(orderId);
                order.setOpened(false);
                order.setClosedTimeStamp(new Date());
                update(order);
        }

        @Override
        public List<Order> getAll(int start, int length) {
                return orderDAO.getAll(start, length);
        }

        @Transactional
        @Override
        public long count() {
                return orderDAO.count();
        }

        @Transactional
        @Override
        public Map<Dish, Integer> getDishes(int orderId) {
                Order order = orderDAO.getById(orderId);
                return order.getDishes();
        }

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
                                storeHouseDAO.decreaseQuantity(ingredient, quantityRequired * quantityOrdered);
                        }
                }
        }

        public ArrayNode convertAllInJSONArray(List<Order> orders) throws IOException {
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode ordersArray = mapper.createArrayNode();
                for (Order order : orders) {
                        ordersArray.add(order.toJSONArray());
                }
                return ordersArray;
        }

        public void handleRequest(HttpServletRequest request) {
                String draw =  request.getParameter("draw");
                int start = Integer.parseInt(request.getParameter("start"));
                int length = Integer.parseInt(request.getParameter("length"));
                String order = request.getParameter("order[0][column]");
                String dir = request.getParameter("order[0][dir]");
                String search =request.getParameter("search[value]");
        }
}
