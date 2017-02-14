package com.goit.popov.restaurant.dao.impl;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.EmployeeDAO;
import com.goit.popov.restaurant.dao.OrderDAO;
import com.goit.popov.restaurant.model.*;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import com.goit.popov.restaurant.service.dataTables.dao.OrderByWaiterServerSideDAOProcessing;
import com.goit.popov.restaurant.service.dataTables.dao.OrderServerSideDAOProcessing;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
        private EmployeeDAO employeeDAO;

        @Autowired
        private OrderServerSideDAOProcessing orderServerSideDAOProcessing;

        @Autowired
        private OrderByWaiterServerSideDAOProcessing orderByWaiterServerSideDAOProcessing;

        @Override
        public Long insert(Order order) {
                return (Long) sessionFactory.getCurrentSession().save(order);
        }

        @Override
        public void update(Order order) {
                sessionFactory.getCurrentSession().update(order);
        }

        @Override
        public Order getById(Long id) {
                return sessionFactory.getCurrentSession().get(Order.class, id);
        }

        @Override
        public void delete(Order order) {
                sessionFactory.getCurrentSession().delete(order);
        }

        @Override
        public Long count() {
              return ((Long) sessionFactory.getCurrentSession().createQuery("select count(*) from Order").uniqueResult());
        }

        public Long countWaiter(Employee waiter) {
            return ((Long) sessionFactory.getCurrentSession().createQuery("select count(*) from Order o where o.waiter=:waiter")
                    .setParameter("waiter", waiter)
                    .uniqueResult());
        }

        @Override
        public List<Order> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select distinct o from Order o order by o.id").list();
        }

        @Override
        public List<Order> getAllWaiterToday(Long waiterId) {
                Employee waiter = employeeDAO.getById(waiterId);
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
