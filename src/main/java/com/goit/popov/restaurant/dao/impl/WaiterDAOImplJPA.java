package com.goit.popov.restaurant.dao.impl;

import com.goit.popov.restaurant.dao.entity.WaiterDAO;
import com.goit.popov.restaurant.model.Waiter;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrey on 11/5/2016.
 */
@Transactional
public class WaiterDAOImplJPA implements WaiterDAO {

        @Autowired
        private SessionFactory sessionFactory;

        @Override
        public int insert(Waiter waiter) {
                return (int) sessionFactory.getCurrentSession().save(waiter);
        }

        @Override
        public void update(Waiter waiter) {
                sessionFactory.getCurrentSession().update(waiter);
        }

        @Override
        public List<Waiter> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select w from Waiter w").list();
        }

        @Override
        public Waiter getById(int id) {
                return sessionFactory.getCurrentSession().get(Waiter.class, id);
        }

        @Override
        public void delete(Waiter waiter) {
                sessionFactory.getCurrentSession().delete(waiter);
        }

        @Override
        public Waiter getByName(String name) {
                Query query = sessionFactory.getCurrentSession().createQuery("select e from Waiter e " +
                        "where e.name like :name");
                query.setParameter("name", name);
                return (Waiter) query.uniqueResult();
        }
}
