package com.goit.popov.restaurant.dao.impl;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.PreparedDishHistoryDAO;
import com.goit.popov.restaurant.model.*;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * Created by Andrey on 10/28/2016.
 */
@Transactional
public class PreparedDishHistoryDAOImplJPA implements PreparedDishHistoryDAO {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(PreparedDishHistoryDAOImplJPA.class);

        @Autowired
        private SessionFactory sessionFactory;

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
        public long getPreparedDishesQuantity(Order order) {
                return (long) sessionFactory.getCurrentSession().createQuery("select distinct count(pd) from PreparedDish pd " +
                        "where pd.order=:order")
                        .setParameter("order", order)
                        .getSingleResult();
        }

        @Override
        public long getPreparedDishesQuantity(Dish dish, Order order) {
                return (long) sessionFactory.getCurrentSession().createQuery("select distinct count(pd) from PreparedDish pd " +
                        "where pd.order=:order and pd.dish=:dish and pd.isCancelled=false")
                        .setParameter("dish", dish)
                        .setParameter("order", order)
                        .getSingleResult();
        }

        @Override
        public long getCancelledDishesQuantity(Dish dish, Order order) {
                return (long) sessionFactory.getCurrentSession().createQuery("select distinct count(pd) from PreparedDish pd " +
                        "where pd.order=:order and pd.dish=:dish and pd.isCancelled=true")
                        .setParameter("dish", dish)
                        .setParameter("order", order)
                        .getSingleResult();
        }

        /*@Override
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
        }*/

}
