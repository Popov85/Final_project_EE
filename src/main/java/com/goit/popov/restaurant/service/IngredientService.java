package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.IngredientDAO;
import com.goit.popov.restaurant.dao.StoreHouseDAO;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.StoreHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrey on 09.11.2016.
 */
public class IngredientService implements IngredientDAO {

        @Autowired
        IngredientDAO ingredientDAO;

        @Autowired
        private StoreHouseDAO storeHouseDAO;

        @Override
        public List<Ingredient> getAll() {
                return ingredientDAO.getAll();
        }

        @Override
        public Ingredient getById(Long id) {
                return ingredientDAO.getById(id);
        }

        @Override
        public void delete(Ingredient ingredient) {
                ingredientDAO.delete(ingredient);
        }

        // TODO to finish
        @Transactional
        @Override
        public Long insert(Ingredient ingredient) {
                Long newIngredientId = 1L;
                try {
                        newIngredientId = ingredientDAO.insert(ingredient);
                        StoreHouse sh = new StoreHouse(ingredient, 0);
                        System.out.println("sh = "+sh);
                        storeHouseDAO.insert(sh);
                } catch (Exception e) {
                        System.out.println("ERROR: "+e.getMessage()+" cause: "+e.getClass());
                }
                return newIngredientId;
        }

        @Override
        public void update(Ingredient ingredient) {
                ingredientDAO.update(ingredient);
        }

        // TODO to check
        @Transactional
        public void deleteById(Long id) {
                StoreHouse storeHouse = storeHouseDAO.getByIngredient(getById(id));
                storeHouseDAO.delete(storeHouse);
                delete(getById(id));
        }
}
