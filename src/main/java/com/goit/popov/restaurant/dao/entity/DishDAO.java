package com.goit.popov.restaurant.dao.entity;

import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Ingredient;

import java.util.Map;

/**
 * DishDAO interface
 * @Author: Andrey P.
 * @version 1.0
 */
public interface DishDAO extends GenericDAO<Dish> {

        //Gets all the ingredients needed to prepare a Dish
        Map<Ingredient, Double> getIngredients(int id);

        /**
         * @param: id - Dish we want to check
         * @param: number - quantity of dishes
         * */
        boolean validateIngredients(int id, int number);
}
