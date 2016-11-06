package com.goit.popov.restaurant.dao.impl;

import com.goit.popov.restaurant.dao.entity.DishDAO;
import com.goit.popov.restaurant.dao.entity.StoreHouseDAO;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Ingredient;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 28.10.2016.
 */
public class DishDAOImplJPA implements DishDAO {

        private SessionFactory sessionFactory;

        private StoreHouseDAO stock;

        public void setStock(StoreHouseDAO stock) {
                this.stock = stock;
        }

        public void setSessionFactory(SessionFactory sessionFactory) {
                this.sessionFactory = sessionFactory;
        }

        @Transactional
        @Override
        public int insert(Dish dish) {
                return (int) sessionFactory.getCurrentSession().save(dish);
        }

        @Transactional
        @Override
        public void update(Dish dish) {
                sessionFactory.getCurrentSession().update(dish);
        }

        @Transactional
        @Override
        public List<Dish> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select d from Dish d").list();
        }

        @Transactional
        @Override
        public Dish getById(int id) {
                return sessionFactory.getCurrentSession().get(Dish.class, id);
        }

        @Transactional
        @Override
        public void delete(Dish dish) {
                sessionFactory.getCurrentSession().delete(dish);
        }

        @Transactional
        @Override
        public Map<Ingredient, Double> getIngredients(int id) {
                Dish dish = getById(id);
                return dish.getIngredients();
        }

        @Transactional
        @Override
        public boolean validateIngredients(int id, int number) {
                Map<Ingredient, Double> ingredients = getIngredients(id);
                for (Map.Entry<Ingredient, Double> entry : ingredients.entrySet()) {
                        Ingredient ingredient = entry.getKey();
                        Double quantityRequired = number * entry.getValue();
                        Double quantityInStock = stock.getById(ingredient.getId()).getQuantity();
                        /*For each ingredient of the dish (id) compare how much of the ingredient there is in stock
                         and how much is actually required to cook the dish*/
                        if (quantityInStock < quantityRequired) {
                                return false;
                        }
                }
                return true;
        }
}
