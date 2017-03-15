package com.goit.popov.restaurant.dao.impl;

import com.goit.popov.restaurant.dao.IngredientDAO;
import com.goit.popov.restaurant.dao.StoreHouseDAO;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.StoreHouse;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by Andrey on 28.10.2016.
 */
@Transactional
public class IngredientDAOImplJPA implements IngredientDAO {

        @Autowired
        private SessionFactory sessionFactory;

        @Override
        public Long insert(Ingredient ingredient) {
                return (Long) sessionFactory.getCurrentSession().save(ingredient);
        }

        @Override
        public void update(Ingredient ingredient) {
                sessionFactory.getCurrentSession().update(ingredient);
        }

        @Override
        public List<Ingredient> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select i from Ingredient i").list();
        }

        @Override
        public Ingredient getById(Long id) {
                return sessionFactory.getCurrentSession().get(Ingredient.class, id);
        }

        @Override
        public void delete(Ingredient ingredient) {
                sessionFactory.getCurrentSession().delete(ingredient);
        }
}
