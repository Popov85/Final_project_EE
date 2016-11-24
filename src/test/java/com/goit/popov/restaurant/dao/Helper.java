package com.goit.popov.restaurant.dao;

import com.goit.popov.restaurant.model.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;

/**
 * Created by Andrey on 28.10.2016.
 */
public class Helper {

        public static final SimpleDateFormat format =
                new SimpleDateFormat ("yyyy-MM-dd");

        @Autowired
        private SessionFactory sessionFactory;

        public void setSessionFactory(SessionFactory sessionFactory) {
                this.sessionFactory = sessionFactory;
        }

        @Transactional
        public Position getByIdPosition(int id) {
                return sessionFactory.getCurrentSession().get(Position.class, id);
        }

        @Transactional
        public Employee getByIdEmployee(int id) {
                return sessionFactory.getCurrentSession().get(Employee.class, id);
        }

        @Transactional
        public Waiter getByIdWaiter(int id) {
                return sessionFactory.getCurrentSession().get(Waiter.class, id);
        }

        @Transactional
        public Dish getByIdDish(int id) {
                return sessionFactory.getCurrentSession().get(Dish.class, id);
        }
        @Transactional
        public Order getByIdOrder(int id) {
                return sessionFactory.getCurrentSession().get(Order.class, id);
        }

        @Transactional
        public void insertPosition(Position position) {
                sessionFactory.getCurrentSession().save(position);
        }

        @Transactional
        public void deletePosition(Position position) {
                sessionFactory.getCurrentSession().remove(position);
        }

        @Transactional
        public void insertIngredient(Ingredient ingredient) {
                sessionFactory.getCurrentSession().save(ingredient);
        }

        @Transactional
        public void deleteIngredient(Ingredient ingredient) {
                sessionFactory.getCurrentSession().remove(ingredient);
        }

        @Transactional
        public void insertDish(Dish dish) {
                sessionFactory.getCurrentSession().save(dish);
        }

        @Transactional
        public void deleteDish(Dish dish) {
                sessionFactory.getCurrentSession().remove(dish);
        }

        @Transactional
        public void insertWaiter(Waiter waiter) {
                sessionFactory.getCurrentSession().save(waiter);
        }

        @Transactional
        public void deleteWaiter(Waiter waiter) {
                sessionFactory.getCurrentSession().remove(waiter);
        }


        @Transactional
        public void insertOrder(Order order) {
                sessionFactory.getCurrentSession().save(order);
        }

        @Transactional
        public void deleteOrder(Order order) {
                sessionFactory.getCurrentSession().remove(order);
        }

        @Transactional
        public void insertUnit(Unit unit) {
                sessionFactory.getCurrentSession().save(unit);
        }

        @Transactional
        public void deleteUnit(Unit unit) {
                sessionFactory.getCurrentSession().remove(unit);
        }
}
