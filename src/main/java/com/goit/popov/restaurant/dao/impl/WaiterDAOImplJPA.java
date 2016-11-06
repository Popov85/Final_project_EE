package com.goit.popov.restaurant.dao.impl;

import com.goit.popov.restaurant.dao.entity.WaiterDAO;
import com.goit.popov.restaurant.model.Waiter;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrey on 11/5/2016.
 */
public class WaiterDAOImplJPA implements WaiterDAO {

        private SessionFactory sessionFactory;

        public void setSessionFactory(SessionFactory sessionFactory) {
                this.sessionFactory = sessionFactory;
        }

        @Transactional
        @Override
        public int insert(Waiter waiter) {
                return (int) sessionFactory.getCurrentSession().save(waiter);
        }

        @Transactional
        @Override
        public void update(Waiter waiter) {
                sessionFactory.getCurrentSession().update(waiter);
        }

        @Transactional
        @Override
        public List<Waiter> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select e from Waiter e").list();
        }

        @Transactional
        @Override
        public Waiter getById(int id) {
                return sessionFactory.getCurrentSession().get(Waiter.class, id);
        }

        @Transactional
        @Override
        public void delete(Waiter waiter) {
                sessionFactory.getCurrentSession().delete(waiter);
        }

        @Transactional
        @Override
        public Waiter getByName(String name) {
                Query query = sessionFactory.getCurrentSession().createQuery("select e from Waiter e " +
                        "where e.name like :name");
                query.setParameter("name", name);
                return (Waiter) query.uniqueResult();
        }
}
