package com.goit.popov.restaurant.dao.impl;

import com.goit.popov.restaurant.dao.entity.PositionDAO;
import com.goit.popov.restaurant.model.Position;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrey on 27.10.2016.
 */
@Transactional
public class PositionDAOImplJPA implements PositionDAO {

        @Autowired
        private SessionFactory sessionFactory;

        @Override
        public int insert(Position position) {
                return (int) sessionFactory.getCurrentSession().save(position);
        }

        @Override
        public void update(Position position) {
                sessionFactory.getCurrentSession().update(position);
        }

        @Override
        public List<Position> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select p from Position p").list();
        }

        @Override
        public Position getById(int id) {
                return sessionFactory.getCurrentSession().get(Position.class, id);
        }

        @Override
        public void delete(Position position) {
                sessionFactory.getCurrentSession().delete(position);
        }

        @Override
        public Position getPositionByName(String name) {
                Query query = sessionFactory.getCurrentSession().createQuery("select p from Position p " +
                        "where p.name like :name");
                query.setParameter("name", name);
                return (Position) query.uniqueResult();
        }
}
