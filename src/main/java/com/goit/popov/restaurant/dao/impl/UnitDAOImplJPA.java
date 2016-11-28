package com.goit.popov.restaurant.dao.impl;

import com.goit.popov.restaurant.dao.entity.UnitDAO;
import com.goit.popov.restaurant.model.Unit;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrey on 11/28/2016.
 */
@Transactional
public class UnitDAOImplJPA implements UnitDAO {

        private SessionFactory sessionFactory;

        public void setSessionFactory(SessionFactory sessionFactory) {
                this.sessionFactory = sessionFactory;
        }

        @Override
        public List<Unit> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select i from Unit i").list();
        }

        @Override
        public Unit getById(int id) {
                return sessionFactory.getCurrentSession().get(Unit.class, id);
        }
}
