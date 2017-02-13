package com.goit.popov.restaurant.dao.impl;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.OrderDAO;
import com.goit.popov.restaurant.model.*;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import com.goit.popov.restaurant.service.dataTables.dao.OrderByWaiterServerSideDAOProcessing;
import com.goit.popov.restaurant.service.dataTables.dao.OrderServerSideDAOProcessing;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import java.util.*;

/**
 * Created by Andrey on 10/28/2016.
 */
@Transactional
public class OrderDAOImplJPA implements OrderDAO {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(OrderDAOImplJPA.class);

        @Autowired
        private SessionFactory sessionFactory;

        @Autowired
        private OrderServerSideDAOProcessing orderServerSideDAOProcessing;

        @Autowired
        private OrderByWaiterServerSideDAOProcessing orderByWaiterServerSideDAOProcessing;

        @PersistenceContext(unitName = "entityManagerFactory")
        private EntityManager em;

        @Override
        public int insert(Order order) {
                return (int) sessionFactory.getCurrentSession().save(order);
        }

        @Override
        public void update(Order order) {
                sessionFactory.getCurrentSession().update(order);
        }

        @Override
        public Order getById(int id) {
                return sessionFactory.getCurrentSession().get(Order.class, id);
        }

        @Override
        public void delete(Order order) {
                sessionFactory.getCurrentSession().delete(order);
        }

        @Override
        public void delete(int orderId) {
                Session session = sessionFactory.getCurrentSession();
                Order myObject = (Order)session.load(Order.class,orderId);
                session.delete(myObject);
                //This makes the pending delete to be done
                session.flush() ;
        }

        @Override
        public void close(Order order) {
                order.setOpened(false);
                update(order);
        }

        @Override
        public List<Order> getAllClosed() {
                Query query = sessionFactory.getCurrentSession().createQuery("select o from Order o" +
                        "where o.isOpened = false");
                return query.list();
        }

        @Override
        public List<Order> getAllOpened() {
                Query query = sessionFactory.getCurrentSession().createQuery("select o from Order o " +
                        "where o.isOpened = true");
                return query.list();
        }

        @Override
        public long count() {
              return ((long) sessionFactory.getCurrentSession().createQuery("select count(*) from Order").uniqueResult());
        }

        public long countWaiter(Employee waiter) {
            return ((long) sessionFactory.getCurrentSession().createQuery("select count(*) from Order o where o.waiter=:waiter")
                    .setParameter("waiter", waiter)
                    .uniqueResult());
        }

        @Override
        public List<Order> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select distinct o from Order o order by o.id").list();
        }

        @Override
        public List<PreparedDish> getAllWithPreparedDishes() {
                return sessionFactory.getCurrentSession().createQuery("select o.preparedDishes from Order o").list();
        }

        @Override
        public List<Order> getAllWaiterToday(int waiterId) {
                Employee waiter = new Employee();
                waiter.setId(waiterId);
                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);
                return sessionFactory.getCurrentSession().createQuery("select o from Order o where o.waiter = :waiter"+
                        " and o.openedTimeStamp >= :today")
                        .setParameter("waiter", waiter)
                        .setParameter("today", today, TemporalType.DATE)
                        .list();
        }

        @Override
        public List<Order> getAllToday() {
                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);
                return sessionFactory.getCurrentSession().createQuery("select o from Order o where "+
                        "o.openedTimeStamp >= :today")
                        .setParameter("today", today, TemporalType.DATE)
                        .list();
        }

        @Override
        public List<Order> getAll(DataTablesInputExtendedDTO dt) {
                return orderServerSideDAOProcessing.getAllItems(dt);
        }

        @Override
        public List<Order> getAllOrdersByWaiter(DataTablesInputExtendedDTO dt, String[] params) {
                return orderByWaiterServerSideDAOProcessing.getAllItems(dt, params);
        }

}
