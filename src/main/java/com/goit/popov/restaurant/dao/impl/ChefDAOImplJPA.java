package com.goit.popov.restaurant.dao.impl;

import com.goit.popov.restaurant.dao.entity.ChefDAO;
import com.goit.popov.restaurant.model.Chef;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by Andrey on 11/5/2016.
 */
@Transactional
public class ChefDAOImplJPA implements ChefDAO {

        @Autowired
        private SessionFactory sessionFactory;

        @Override
        public int insert(Chef chef) {
                return (int) sessionFactory.getCurrentSession().save(chef);
        }

        @Override
        public void update(Chef chef) {
                sessionFactory.getCurrentSession().update(chef);
        }

        @Override
        public List<Chef> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select e from Chef e").list();
        }

        @Override
        public Chef getById(int id) {
                return sessionFactory.getCurrentSession().get(Chef.class, id);
        }

        @Override
        public void delete(Chef chef) {
                sessionFactory.getCurrentSession().delete(chef);
        }

        @Override
        public Chef getByName(String name) {
                Query query = sessionFactory.getCurrentSession().createQuery("select e from Chef e " +
                        "where e.name like :name");
                query.setParameter("name", name);
                return (Chef) query.uniqueResult();
        }
}
