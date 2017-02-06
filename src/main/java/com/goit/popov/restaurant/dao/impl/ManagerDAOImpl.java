package com.goit.popov.restaurant.dao.impl;

import com.goit.popov.restaurant.dao.entity.ManagerDAO;
import com.goit.popov.restaurant.model.Manager;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * Created by Andrey on 11/20/2016.
 */
public class ManagerDAOImpl implements ManagerDAO {

        @Autowired
        private SessionFactory sessionFactory;

        @Override
        public int insert(Manager manager) {
               return (int) sessionFactory.getCurrentSession().save(manager);
        }

        @Override
        public void update(Manager manager) {
                sessionFactory.getCurrentSession().update(manager);
        }

        @Override
        public List<Manager> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select e from Manager e").list();
        }

        @Override
        public Manager getById(int id) {
                return sessionFactory.getCurrentSession().get(Manager.class, id);
        }

        @Override
        public void delete(Manager manager) {
                sessionFactory.getCurrentSession().delete(manager);
        }
}
