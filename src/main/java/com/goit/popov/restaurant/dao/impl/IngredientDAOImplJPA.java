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

        @Autowired
        private StoreHouseDAO storeHouseDAO;

        @Override
        public Long insert(Ingredient ingredient) {
                Long key = (Long) sessionFactory.getCurrentSession().save(ingredient);
                // Insert a new StoreHouse entry as well
                StoreHouse stock = new StoreHouse();
                stock.setIngredient(ingredient);
                stock.setQuantity(0);
                sessionFactory.getCurrentSession().save(stock);
                return key;
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
                StoreHouse storeHouse = storeHouseDAO.getByIngredient(ingredient);
                sessionFactory.getCurrentSession().delete(storeHouse);
                sessionFactory.getCurrentSession().delete(ingredient);
        }
}
