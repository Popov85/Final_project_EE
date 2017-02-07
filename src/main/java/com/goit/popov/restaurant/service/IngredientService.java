package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.entity.IngredientDAO;
import com.goit.popov.restaurant.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * Created by Andrey on 09.11.2016.
 */
public class IngredientService implements IngredientDAO {

        @Autowired
        IngredientDAO ingredientDAO;

        @Override
        public List<Ingredient> getAll() {
                return ingredientDAO.getAll();
        }

        @Override
        public Ingredient getById(int id) {
                return ingredientDAO.getById(id);
        }

        @Override
        public void delete(Ingredient ingredient) {
                ingredientDAO.delete(ingredient);
        }

        @Override
        public int insert(Ingredient ingredient) {
                return ingredientDAO.insert(ingredient);
        }

        @Override
        public void update(Ingredient ingredient) {
                ingredientDAO.update(ingredient);
        }

        public void deleteById(int id) {
                ingredientDAO.delete(getById(id));
        }
}
