package com.goit.popov.restaurant.dao.impl;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.PreparedDishHistoryDAO;
import com.goit.popov.restaurant.model.*;
import com.goit.popov.restaurant.service.StockService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.TemporalType;
import java.util.*;

/**
 * Created by Andrey on 10/28/2016.
 */
@Transactional
public class PreparedDishHistoryDAOImplJPA implements PreparedDishHistoryDAO {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(PreparedDishHistoryDAOImplJPA.class);

        private SessionFactory sessionFactory;

        public void setSessionFactory(SessionFactory sessionFactory) {
                this.sessionFactory = sessionFactory;
        }

        @Autowired
        private StockService stockService;

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
        public long count() {
                return (long) sessionFactory.getCurrentSession().createQuery("select count(pd) from PreparedDish pd").uniqueResult();
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
        public List<PreparedDish> getAllPreparedDish(Order order) {
                return sessionFactory.getCurrentSession().createQuery("select pd from PreparedDish pd " +
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

        /**
         * @see "http://docs.jboss.org/hibernate/orm/3.5/javadocs/org/hibernate/Session.html"
         * @param preparedDishes
         */
        @Override
        public void confirmDishPrepared(Set<PreparedDish> preparedDishes){
                Session session = sessionFactory.openSession();
                Transaction tx = session.beginTransaction();
                for (PreparedDish preparedDish : preparedDishes) {
                        session.save(preparedDish);
                        // Decrease ingredients in stock
                        decreaseQuantity(preparedDish
                                .getDish()
                                .getIngredients());
                }
                tx.commit();
                session.close();

                /*try {
                        tx = session.beginTransaction();
                        for (PreparedDish preparedDish : preparedDishes) {
                                session.save(preparedDish);
                                // Decrease ingredients in stock
                                decreaseQuantity(preparedDish
                                        .getDish()
                                        .getIngredients());
                        }
                        tx.commit();
                }
                catch (Exception e) {
                        if (tx!=null) tx.rollback();
                        throw e;
                }
                finally {
                        session.close();
                }*/
        }

        private void decreaseQuantity(Map<Ingredient, Double> ingredients) {
                for (Map.Entry<Ingredient, Double> entry : ingredients.entrySet()) {
                        Ingredient ingredient = entry.getKey();
                        Double quantityRequired = entry.getValue();
                        stockService.decreaseQuantity(ingredient, quantityRequired);
                }
        }
}
