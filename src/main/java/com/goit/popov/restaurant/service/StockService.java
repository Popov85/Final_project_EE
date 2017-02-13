package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.StoreHouseDAO;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.service.dataTables.DataTablesSearchable;

import java.util.Map;

/**
 * Created by Andrey on 30.01.2017.
 */
public interface StockService extends StoreHouseDAO, DataTablesSearchable {

        void decreaseIngredients(Map<Ingredient, Double> ingredientsRequired);
        void increaseIngredients(Map<Ingredient, Double> ingredientsReturned);
        void decreaseIngredient(Ingredient ingredient, Double quantityRequired);
        void increaseIngredient(Ingredient ingredient, Double quantityReturned);
}
