package com.goit.popov.restaurant.dao.impl;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.OrderDAO;
import com.goit.popov.restaurant.model.*;
import com.goit.popov.restaurant.model.Order;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
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

        @PersistenceContext(unitName = "entityManagerFactory")
        private EntityManager em;

        public void setSessionFactory(SessionFactory sessionFactory) {
                this.sessionFactory = sessionFactory;
        }

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

        @Override
        // TODO
        public long count(String search) {
                return ((long) sessionFactory.getCurrentSession().createQuery("select count(*) from Order").uniqueResult());
        }

        @Override
        public List<Order> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select o from Order o").list();
        }

        @Override
        public List<Order> getAll(int start, int length) {
                List<Order> subset = sessionFactory.getCurrentSession().createQuery(
                        "from Order")
                        .setFirstResult(start)
                        .setMaxResults(length).list();
                return subset;
        }

        @Override
        public List<Order> getAll(int start, int length, String orderColumn, String direction) {
                List<Order> result = new ArrayList<>();
                logger.info("em: "+em);
                try {
                        CriteriaBuilder builder = em.getCriteriaBuilder();
                        CriteriaQuery<Order> criteriaQuery = builder.createQuery( Order.class );
                        Root<Order> orderRoot = criteriaQuery.from( Order.class );
                        criteriaQuery.select(orderRoot);
                        criteriaQuery.distinct(true);
                        if (direction.equals("asc")) {
                                criteriaQuery.orderBy(builder.asc(orderRoot.get(orderColumn)));
                        } else {
                                criteriaQuery.orderBy(builder.desc(orderRoot.get(orderColumn)));
                        }
                        final TypedQuery<Order> typedQuery = em.createQuery(criteriaQuery);
                        typedQuery.setFirstResult( start ).setMaxResults( length );
                        result = typedQuery.getResultList();
                } catch (Exception e) {
                        logger.error("ERROR: "+e.getMessage());
                }
                return result;

                /*Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Order.class);
                criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
                criteria.setFirstResult(start);
                criteria.setMaxResults(length);
                if (direction.equals("asc")) {
                        criteria.addOrder(org.hibernate.criterion.Order.asc(orderColumn));
                } else {
                        criteria.addOrder(org.hibernate.criterion.Order.desc(orderColumn));
                }
                return criteria.list();*/

        }

        @Override
        public List<Order> getAll(int start, int length, String orderColumn, String direction, String search) {
                List<Order> result = new ArrayList<>();
                logger.info("em: "+em);
                try {
                        CriteriaBuilder builder = em.getCriteriaBuilder();
                        CriteriaQuery<Order> criteriaQuery = builder.createQuery( Order.class );
                        Root<Order> orderRoot = criteriaQuery.from( Order.class );
                        criteriaQuery.select(orderRoot);
                        criteriaQuery.distinct(true);
                        if (direction.equals("asc")) {
                                criteriaQuery.orderBy(builder.asc(orderRoot.get(orderColumn)));
                        } else {
                                criteriaQuery.orderBy(builder.desc(orderRoot.get(orderColumn)));
                        }
                        criteriaQuery.where( builder.like( orderRoot.<String>get("waiter").get("name"), search+"%" ) );
                        final TypedQuery<Order> typedQuery = em.createQuery(criteriaQuery);
                        typedQuery.setFirstResult( start ).setMaxResults( length );
                        result = typedQuery.getResultList();
                } catch (Exception e) {
                        logger.error("ERROR: "+e.getMessage());
                }
                return result;

                /*Criteria criteria = null;
                try {
                        criteria = sessionFactory.getCurrentSession().createCriteria(Order.class);
                        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
                        criteria.add(Restrictions.gt("id", Integer.valueOf(search)));
                        criteria.setFirstResult(start);
                        criteria.setMaxResults(length);
                        if (direction.equals("asc")) {
                                criteria.addOrder(org.hibernate.criterion.Order.asc(orderColumn));
                        } else {
                                criteria.addOrder(org.hibernate.criterion.Order.desc(orderColumn));
                        }
                        List<Order> orders = criteria.list();
                        System.out.println("Size is "+orders.size());
                        for (Order order : orders) {
                                System.out.println(order.toString());
                        }
                        logger.info("OK, got all orders at getAll() with the search parameter");
                } catch (Throwable e) {
                        logger.error("ERROR: "+e.getMessage());
                }
                return criteria.list();*/
        }
}
