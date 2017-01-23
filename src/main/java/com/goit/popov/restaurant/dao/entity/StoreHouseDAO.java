package com.goit.popov.restaurant.dao.entity;

import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.StoreHouse;

import java.util.List;

/**
 * StoreHouseDAO interface for basic CRUD operations
 * @Author: Andrey P.
 * @version 1.0
 */
public interface StoreHouseDAO extends GenericDAO<StoreHouse> {
        /*
        Obtains all the ingredients that are present in stock
        in very small quantities (less than a predefined threshold
        value, say 10 kg/L)
         */
        List<StoreHouse> getAllRunOut(double threshold);

        StoreHouse getByIngredient(Ingredient ingredient);

        Double getQuantityByIngredient(Ingredient ingredient);

}
