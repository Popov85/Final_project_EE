package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.entity.IngredientDAO;
import com.goit.popov.restaurant.model.Ingredient;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrey on 09.11.2016.
 */
public class IngredientService {
        IngredientDAO ingredientDAO;

        public void setIngredientDAO(IngredientDAO ingredientDAO) {
                this.ingredientDAO = ingredientDAO;
        }
        @Transactional
        public void save(Ingredient ingredient) {
                ingredientDAO.insert(ingredient);
        }

        @Transactional
        public void deleteById(int id) {
                ingredientDAO.delete(getIngredientById(id));
        }

        @Transactional
        public Ingredient getIngredientById(int id) {
               return ingredientDAO.getById(id);
        }

        @Transactional
        public List<Ingredient> getAll() {
                return ingredientDAO.getAll();
        }
        @Transactional
        public void update(Ingredient ingredient) {
                ingredientDAO.update(ingredient);
        }
}
