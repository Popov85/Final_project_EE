package com.goit.popov.restaurant.dao.impl;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.OrderDAO;
import com.goit.popov.restaurant.model.*;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.dataTablesDTO.DataTablesInputDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 10/28/2016.
 */
@Transactional
public class OrderDAOImplJPA implements OrderDAO {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(OrderDAOImplJPA.class);

        private SessionFactory sessionFactory;

        public void setSessionFactory(SessionFactory sessionFactory) {
                this.sessionFactory = sessionFactory;
        }

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
        public void addDish(Order order, Dish dish, int quantity) {
                Map<Dish, Integer> dishes = order.getDishes();
                dishes.put(dish, quantity);
                update(order);
        }

        @Override
        public void deleteDish(Order order, Dish dish, int quantity) {
                Map<Dish, Integer> dishes = order.getDishes();
                dishes.remove(dish, quantity);
                update(order);
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
                Query query = sessionFactory.getCurrentSession().createQuery("select o from Order o" +
                        "where o.isOpened = true");
                return query.list();
        }

        @Override
        public long count() {
              return ((long) sessionFactory.getCurrentSession().createQuery("select count(*) from Order").uniqueResult());
        }


        @Deprecated
        @Override
        public List<Order> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select o from Order o").list();
        }

        @Override
        public List<Order> getAll(DataTablesInputDTO dt) {
                List<Order> resultOrders = new ArrayList<>();
                try {
                        CriteriaBuilder builder = em.getCriteriaBuilder();
                        CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
                        Root<Order> orderRoot = criteriaQuery.from(Order.class);
                        criteriaQuery.select(orderRoot);
                        criteriaQuery.distinct(true);
                        criteriaQuery = toFilter(dt, builder, criteriaQuery, orderRoot);
                        criteriaQuery = toSort(dt, builder, criteriaQuery, orderRoot);
                        resultOrders = toPage(dt, criteriaQuery).getResultList();
                } catch (Exception e) {
                        logger.error("ERROR: " + e.getMessage());
                }
                return resultOrders;
        }

        private CriteriaQuery<Order> toFilter(DataTablesInputDTO dt, CriteriaBuilder builder,
                                              CriteriaQuery<Order> criteriaQuery, Root<Order> orderRoot) {
                criteriaQuery.where(builder.like(orderRoot.<String>get("waiter").get("name"), dt.getSearch() + "%"));
                return criteriaQuery;
        }

        private CriteriaQuery<Order> toSort(DataTablesInputDTO dt, CriteriaBuilder builder,
                                            CriteriaQuery<Order> criteriaQuery, Root<Order> orderRoot) {
                if (dt.getDir().equals("asc")) {
                        criteriaQuery.orderBy(builder.asc(orderRoot.get(dt.getColumnName())));
                } else {
                        criteriaQuery.orderBy(builder.desc(orderRoot.get(dt.getColumnName())));
                }
                return criteriaQuery;
        }

        private TypedQuery<Order> toPage(DataTablesInputDTO dt,
                                         CriteriaQuery<Order> criteriaQuery) {
                final TypedQuery<Order> typedQuery = em.createQuery(criteriaQuery);
                typedQuery.setFirstResult(dt.getStart()).setMaxResults(dt.getLength());
                return typedQuery;
        }

}
