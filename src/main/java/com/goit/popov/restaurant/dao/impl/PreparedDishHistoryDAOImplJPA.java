package com.goit.popov.restaurant.dao.impl;

import com.goit.popov.restaurant.dao.entity.PreparedDishHistoryDAO;
import com.goit.popov.restaurant.model.Chef;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.model.PreparedDish;
import com.goit.popov.restaurant.service.IngredientService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TemporalType;
import java.util.*;

/**
 * Created by Andrey on 10/28/2016.
 */
@Transactional
public class PreparedDishHistoryDAOImplJPA implements PreparedDishHistoryDAO {

        private SessionFactory sessionFactory;

        public void setSessionFactory(SessionFactory sessionFactory) {
                this.sessionFactory = sessionFactory;
        }

        @Autowired
        private IngredientService ingredientService;

        @Override
        public int insert(PreparedDish preparedDish) {
                return (int) sessionFactory.getCurrentSession().save(preparedDish);
        }

        @Override
        public void update(PreparedDish preparedDish) {
                sessionFactory.getCurrentSession().update(preparedDish);
        }

        @Override
        public PreparedDish getById(int id) {
                return sessionFactory.getCurrentSession().get(PreparedDish.class, id);
        }

        @Override
        public void delete(PreparedDish preparedDish) {
                sessionFactory.getCurrentSession().delete(preparedDish);
        }

        @Override
        public List<PreparedDish> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select distinct pd from PreparedDish pd").list();
        }



        @Override
        public int addPreparedDish(PreparedDish dish) {
                return (int) sessionFactory.getCurrentSession().save(dish);
        }

        @Override
        public List<Dish> getAll(Order order) {
                return sessionFactory.getCurrentSession().createQuery("select distinct pd.dish from PreparedDish pd " +
                        "where pd.order=:order")
                        .setParameter("order", order)
                        .list();
        }

        @Override
        public List<Order> getAllOrderForChef() {
                return sessionFactory.getCurrentSession().createQuery("select o from Order o")
                        .list();
        }

        @Override
        public long getPreparedDishesQuantity(Order order) {
                return (long) sessionFactory.getCurrentSession().createQuery("select distinct count(pd) from PreparedDish pd " +
                        "where pd.order=:order")
                        .setParameter("order", order)
                        .getSingleResult();
        }

        @Override
        public long getPreparedDishesQuantity(Dish dish, Order order) {
                return (long) sessionFactory.getCurrentSession().createQuery("select distinct count(pd) from PreparedDish pd " +
                        "where pd.order=:order and pd.dish=:dish")
                        .setParameter("dish", dish)
                        .setParameter("order", order)
                        .getSingleResult();
        }

        @Override
        public List<PreparedDish> getAllChefToday(int chefId) {
                Chef chef = new Chef();
                chef.setId(chefId);
                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);
                return sessionFactory.getCurrentSession().createQuery("select pd from PreparedDish pd " +
                        "where pd.chef = :chef and pd.order.openedTimeStamp >= :today")
                        .setParameter("chef", chef)
                        .setParameter("today", today, TemporalType.DATE)
                        .list();
        }

        @Override
        public void confirmDishPrepared(PreparedDish preparedDish, int quantity) throws InterruptedException {
                for (int i=0; i<quantity; i++) {
                        preparedDish.setWhenPrepared(new Date());
                        Thread.sleep(10);
                        int id = insert(preparedDish);
                        System.out.println("id= "+id);
                        // Decrease ingredients TODO

                        System.out.println("Confirmed!");
                }
        }
}
