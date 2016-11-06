package com.goit.popov.restaurant.dao.impl;

import com.goit.popov.restaurant.dao.entity.IngredientDAO;
import com.goit.popov.restaurant.model.Ingredient;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrey on 28.10.2016.
 */
public class IngredientDAOImplJPA implements IngredientDAO {

        private SessionFactory sessionFactory;

        public void setSessionFactory(SessionFactory sessionFactory) {
                this.sessionFactory = sessionFactory;
        }

        @Transactional
        @Override
        public int insert(Ingredient ingredient) {
                return (int) sessionFactory.getCurrentSession().save(ingredient);
        }

        @Transactional
        @Override
        public void update(Ingredient ingredient) {
                sessionFactory.getCurrentSession().update(ingredient);
        }

        @Transactional
        @Override
        public List<Ingredient> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select i from Ingredient i").list();
        }

        @Transactional
        @Override
        public Ingredient getById(int id) {
                return sessionFactory.getCurrentSession().get(Ingredient.class, id);
        }

        @Transactional
        @Override
        public void delete(Ingredient ingredient) {
                sessionFactory.getCurrentSession().delete(ingredient);
        }
}
