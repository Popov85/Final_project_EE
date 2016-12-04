package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.entity.DishDAO;
import com.goit.popov.restaurant.dao.entity.StoreHouseDAO;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 02.12.2016.
 */
public class DishService implements DishServiceInterface {

        @Autowired
        private DishDAO dishDAO;

        @Autowired
        private StoreHouseDAO storeHouseDAO;

        public void insert(Dish dish) {
                dishDAO.insert(dish);
        }

        public void update(Dish dish) {
                dishDAO.update(dish);
        }

        public List<Dish> getAll() {
                return dishDAO.getAll();
        }

        public Dish getById(int id) {
                return dishDAO.getById(id);
        }

        public void delete(Dish dish) {
                dishDAO.delete(dish);
        }

        @Transactional
        @Override
        public boolean validateIngredients(int id, int number) {
                Map<Ingredient, Double> ingredients = getIngredients(id);
                for (Map.Entry<Ingredient, Double> entry : ingredients.entrySet()) {
                        Ingredient ingredient = entry.getKey();
                        Double quantityRequired = number * entry.getValue();
                        Double quantityInStock = storeHouseDAO.getById(ingredient.getId()).getQuantity();
                        /*For each ingredient of the dish (id) compare how much of the ingredient there is in stock
                         and how much is actually required to cook the dish*/
                        if (quantityInStock < quantityRequired) {
                                return false;
                        }
                }
                return true;
        }

        @Transactional
        @Override
        public Map<Ingredient, Double> getIngredients(int id) {
                Dish dish = getById(id);
                return dish.getIngredients();
        }
}
