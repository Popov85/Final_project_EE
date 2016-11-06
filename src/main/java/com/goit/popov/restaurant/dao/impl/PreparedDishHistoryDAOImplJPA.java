package com.goit.popov.restaurant.dao.impl;

import com.goit.popov.restaurant.dao.entity.PreparedDishHistoryDAO;
import com.goit.popov.restaurant.model.PreparedDish;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrey on 10/28/2016.
 */
public class PreparedDishHistoryDAOImplJPA implements PreparedDishHistoryDAO {

        private SessionFactory sessionFactory;

        public void setSessionFactory(SessionFactory sessionFactory) {
                this.sessionFactory = sessionFactory;
        }

        @Transactional
        @Override
        public int addPreparedDish(PreparedDish dish) {
                return (int) sessionFactory.getCurrentSession().save(dish);
        }

        @Transactional
        @Override
        public List<PreparedDish> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select pd from PreparedDish pd").list();
        }
}
