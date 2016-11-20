package com.goit.popov.restaurant.dao.impl;

import com.goit.popov.restaurant.dao.entity.DishDAO;
import com.goit.popov.restaurant.dao.entity.OrderDAO;
import com.goit.popov.restaurant.dao.entity.StoreHouseDAO;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.Order;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 10/28/2016.
 */
public class OrderDAOImplJPA implements OrderDAO {

        private SessionFactory sessionFactory;

        private DishDAO dish;

        private StoreHouseDAO stock;

        public void setStock(StoreHouseDAO stock) {
                this.stock = stock;
        }

        public void setDish(DishDAO dish) {
                this.dish = dish;
        }

        public void setSessionFactory(SessionFactory sessionFactory) {
                this.sessionFactory = sessionFactory;
        }

        @Transactional
        @Override
        public int insert(Order order) {
                //updateStock(order);
                return (int) sessionFactory.getCurrentSession().save(order);
        }

        @Transactional
        private void updateStock(Order order) {
                Map<Dish, Integer> dishes = order.getDishes();
                for (Map.Entry<Dish, Integer> entry : dishes.entrySet()) {
                        Dish dish = entry.getKey();
                        Integer quantityOrdered = entry.getValue();
                        Map<Ingredient, Double> ingredients = dish.getIngredients();
                        for (Map.Entry<Ingredient, Double> ing : ingredients.entrySet()) {
                                Ingredient ingredient = ing.getKey();
                                Double quantityRequired = ing.getValue();
                                stock.decreaseQuantity(ingredient, quantityRequired * quantityOrdered);
                        }
                }
        }

        @Transactional
        @Override
        public void update(Order order) {
                sessionFactory.getCurrentSession().update(order);
        }

        @Transactional
        @Override
        public List<Order> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select o from Order o").list();
        }

        @Transactional
        @Override
        public Order getById(int id) {
                return sessionFactory.getCurrentSession().get(Order.class, id);
        }

        @Transactional
        @Override
        public void delete(Order order) {
                sessionFactory.getCurrentSession().delete(order);
        }

        @Transactional
        @Override
        public void addDish(Order order, Dish dish, int quantity) {
                Map<Dish, Integer> dishes = order.getDishes();
                dishes.put(dish, quantity);
                update(order);
        }

        @Transactional
        @Override
        public void deleteDish(Order order, Dish dish, int quantity) {
                Map<Dish, Integer> dishes = order.getDishes();
                dishes.remove(dish, quantity);
                update(order);
        }

        @Transactional
        @Override
        public void close(Order order) {
                order.setOpened(false);
                update(order);
        }

        @Transactional
        @Override
        public List<Order> getAllClosed() {
                Query query = sessionFactory.getCurrentSession().createQuery("select o from Order o" +
                        "where o.isOpened = false");
                return query.list();
        }

        @Transactional
        @Override
        public List<Order> getAllOpened() {
                Query query = sessionFactory.getCurrentSession().createQuery("select o from Order o" +
                        "where o.isOpened = true");
                return query.list();
        }


        @Transactional
        public Map<Dish, Integer> getDishes(int id) {
                Order order = getById(id);
                return order.getDishes();
        }

        @Transactional
        @Override
        public boolean validateIngredients(int id) {
                Map<Dish, Integer> dishes = getDishes(id);
                for (Map.Entry<Dish, Integer> entry : dishes.entrySet()) {
                        Dish dish = entry.getKey();
                        Integer quantityOrdered = entry.getValue();
                        if (!this.dish.validateIngredients(dish.getId(), quantityOrdered)) {
                                return false;
                        }
                }
                return true;
        }
}
