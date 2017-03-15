package com.goit.popov.restaurant.dao.impl;

import com.goit.popov.restaurant.dao.UnitDAO;
import com.goit.popov.restaurant.model.Unit;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by Andrey on 11/28/2016.
 */
@Transactional
public class UnitDAOImplJPA implements UnitDAO {

        @Autowired
        private SessionFactory sessionFactory;

        @Override
        public Long insert(Unit unit) {
                return (Long) sessionFactory.getCurrentSession().save(unit);
        }

        @Override
        public void update(Unit unit) {
                sessionFactory.getCurrentSession().update(unit);
        }

        @Override
        public List<Unit> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select u from Unit u").list();
        }

        @Override
        public Unit getById(Long id) {
                return sessionFactory.getCurrentSession().get(Unit.class, id);
        }

        @Override
        public void delete(Unit unit) {
                sessionFactory.getCurrentSession().delete(unit);
        }
}
