package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.entity.IngredientDAO;
import com.goit.popov.restaurant.model.Ingredient;

/**
 * Created by Andrey on 09.11.2016.
 */
public class IngredientService {
        IngredientDAO ingredientDAO;

        public void setIngredientDAO(IngredientDAO ingredientDAO) {
                this.ingredientDAO = ingredientDAO;
        }
        public void save(Ingredient ingredient) {
                ingredientDAO.insert(ingredient);
        }
}
