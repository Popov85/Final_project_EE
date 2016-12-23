package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.goit.popov.restaurant.dao.entity.OrderDAO;
import com.goit.popov.restaurant.dao.entity.StoreHouseDAO;
import com.goit.popov.restaurant.model.*;
import com.goit.popov.restaurant.model.dataTablesAdapter.JSONArrayOfArraysConvertible;
import com.goit.popov.restaurant.service.dataTablesDTO.DataTablesInputDTO;
import com.goit.popov.restaurant.service.dataTablesDTO.DataTablesOutputDTO;
import com.goit.popov.restaurant.service.dataTablesDTO.DataTablesOutputDTOUniversal;
import com.goit.popov.restaurant.service.dataTablesDTO.DataTablesSearchable;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 12/3/2016.
 */
public class OrderService implements OrderServiceInterface, JSONArrayOfArraysConvertible<Order>, DataTablesSearchable {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(OrderService.class);

        @Autowired
        private OrderDAO orderDAO;

        @Autowired
        private DishService dishService;

        @Autowired
        private StoreHouseDAO storeHouseDAO;

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

        @Override
        public ArrayNode toJSONArray(List<Order> orders) {
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode ordersArray = mapper.createArrayNode();
                for (Order order : orders) {
                        ordersArray.add(order.toJSONArray());
                }
                return ordersArray;
        }

        @Override
        public DataTablesOutputDTO getAll(DataTablesInputDTO dt) {
                long recordsTotal = count();
                long recordsFiltered;
                List<Order> orders = orderDAO.getAll(dt);
                if (!dt.getSearch().isEmpty()) {
                        recordsFiltered = orders.size();
                } else {
                        recordsFiltered=recordsTotal;
                }
                return new DataTablesOutputDTO()
                        .setDraw(dt.getDraw())
                        .setRecordsTotal(recordsTotal)
                        .setRecordsFiltered(recordsFiltered)
                        .setData(toJSONArray(orders));
        }

        public DataTablesOutputDTOUniversal<Order> getAllOrders(DataTablesInputDTO dt) {
                long recordsTotal = count();
                long recordsFiltered;
                List<Order> data = orderDAO.getAll(dt);
                if (!dt.getSearch().isEmpty()) {
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
