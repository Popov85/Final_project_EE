package com.goit.popov.restaurant.dao.impl;

import com.goit.popov.restaurant.dao.entity.OrderDAO;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 10/28/2016.
 */
@Transactional
public class OrderDAOImplJPA implements OrderDAO {

        private SessionFactory sessionFactory;

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
                Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Order.class);
                criteria.setFirstResult(start);
                criteria.setMaxResults(length);
                if (direction.equals("asc")) {
                        criteria.addOrder(org.hibernate.criterion.Order.asc(orderColumn));
                } else {
                        criteria.addOrder(org.hibernate.criterion.Order.desc(orderColumn));
                }
                return criteria.list();
        }

        @Override
        public List<Order> getAll(int start, int length, String orderColumn, String direction, String search) {
                // Create CriteriaBuilder
                /*CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
                CriteriaQuery<Order> criteria = builder.createQuery(Order.class);
                criteria.orderBy();*/
                throw new UnsupportedOperationException();
        }
}
