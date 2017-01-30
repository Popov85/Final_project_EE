package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.entity.IngredientDAO;
import com.goit.popov.restaurant.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrey on 09.11.2016.
 */
@Transactional
public class IngredientService {

        @Autowired
        IngredientDAO ingredientDAO;


        public void save(Ingredient ingredient) {
                ingredientDAO.insert(ingredient);
        }

        public void deleteById(int id) {
                ingredientDAO.delete(getIngredientById(id));
        }

        public Ingredient getIngredientById(int id) {
               return ingredientDAO.getById(id);
        }

        public List<Ingredient> getAll() {
                return ingredientDAO.getAll();
        }

        public void update(Ingredient ingredient) {
                ingredientDAO.update(ingredient);
        }
}
