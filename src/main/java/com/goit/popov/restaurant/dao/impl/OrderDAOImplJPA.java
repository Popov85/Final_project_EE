package com.goit.popov.restaurant.dao.impl;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.OrderDAO;
import com.goit.popov.restaurant.model.*;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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


        public long countWaiter(Waiter waiter) {
            return ((long) sessionFactory.getCurrentSession().createQuery("select count(*) from Order o where o.waiter=:waiter")
                    .setParameter("waiter", waiter)
                    .uniqueResult());
        }

        @Override
        public List<Order> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select o from Order o").list();
        }


        // TODO not working
        @Override
        public List<Order> getAllWithPreparedDishes() {
                return sessionFactory.getCurrentSession().createQuery("select o from Order o join o.preparedDishes").list();
        }

        public List<Order> getAllWaiterToday(int waiterId) {
                Waiter waiter = new Waiter();
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

        public List<Order> getAllWaiterArchive(int waiterId, DataTablesInputExtendedDTO dt) {
            List<Order> resultOrders = new ArrayList<>();
            try {
                    CriteriaBuilder builder = em.getCriteriaBuilder();
                    CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
                    Root<Order> orderRoot = criteriaQuery.from(Order.class);
                    criteriaQuery.select(orderRoot);
                    criteriaQuery.distinct(true);
                    criteriaQuery = toFilterWaiter(waiterId, dt, builder, criteriaQuery, orderRoot);
                    criteriaQuery = toSort(dt, builder, criteriaQuery, orderRoot);
                    resultOrders = toPage(dt, criteriaQuery).getResultList();
            } catch (Exception e) {
                    logger.error("ERROR: " + e.getMessage());
            }
            return resultOrders;
        }

        private CriteriaQuery<Order> toFilterWaiter(int waiterId, DataTablesInputExtendedDTO dt, CriteriaBuilder builder,
                                              CriteriaQuery<Order> criteriaQuery, Root<Order> orderRoot) {
                Path<Integer> w = orderRoot.get("waiter").<Integer>get("id");
                Predicate waiter = builder.equal(w, waiterId);
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(waiter);
                if (!dt.getColumnSearch().isEmpty()) {
                        filterDate(dt, builder, orderRoot, predicates);
                        filterTable(dt, builder, orderRoot, predicates);
                }
                criteriaQuery.where(predicates.toArray(new Predicate[]{}));
                return criteriaQuery;
        }

        public List<Order> getAll(DataTablesInputExtendedDTO dt) {
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

        private CriteriaQuery<Order> toFilter(DataTablesInputExtendedDTO dt, CriteriaBuilder builder,
                                              CriteriaQuery<Order> criteriaQuery, Root<Order> orderRoot) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!dt.getColumnSearch().isEmpty()) {
                        filterDate(dt, builder, orderRoot, predicates);
                        filterTable(dt, builder, orderRoot, predicates);
                        if (dt.getColumnSearch().containsKey("waiter")) {
                                String waiter = dt.getColumnSearch().get("waiter");
                                predicates.add(builder.like(orderRoot.<String>get("waiter").get("name"), waiter + "%"));
                        }
                }
                criteriaQuery.where(predicates.toArray(new Predicate[]{}));
                return criteriaQuery;
        }

        private List<Predicate> filterTable(DataTablesInputExtendedDTO dt, CriteriaBuilder builder,
                                            Root<Order> orderRoot, List<Predicate> predicates) {
            if (dt.getColumnSearch().containsKey("table")) {
                    String table = dt.getColumnSearch().get("table");
                    Path<String> t = orderRoot.get("table");
                    predicates.add(builder.equal(t, table));
            }
            return predicates;
        }

        private List<Predicate> filterDate(DataTablesInputExtendedDTO dt, CriteriaBuilder builder,
                                           Root<Order> orderRoot, List<Predicate> predicates) {
                if (dt.getColumnSearch().containsKey("openedTimeStamp")) {
                        String openDate = dt.getColumnSearch().get("openedTimeStamp");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = null;
                        try {
                                date = formatter.parse(openDate);
                        } catch (ParseException e) {
                                logger.error("ERROR" + e.getMessage());
                        }
                        predicates.add(builder.greaterThanOrEqualTo(orderRoot.<Date>get("openedTimeStamp"), date));
                }
                return predicates;
        }

        private CriteriaQuery<Order> toSort(DataTablesInputExtendedDTO dt, CriteriaBuilder builder,
                                            CriteriaQuery<Order> criteriaQuery, Root<Order> orderRoot) {
                if (dt.getDir().equals("asc")) {
                        criteriaQuery.orderBy(builder.asc(orderRoot.get(dt.getColumnName())));
                } else {
                        criteriaQuery.orderBy(builder.desc(orderRoot.get(dt.getColumnName())));
                }
                return criteriaQuery;
        }

        private TypedQuery<Order> toPage(DataTablesInputExtendedDTO dt,
                                         CriteriaQuery<Order> criteriaQuery) {
                final TypedQuery<Order> typedQuery = em.createQuery(criteriaQuery);
                typedQuery
                        .setFirstResult(dt.getStart())
                        .setMaxResults(dt.getLength());
                return typedQuery;
        }
}
