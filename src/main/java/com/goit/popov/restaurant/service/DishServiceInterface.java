package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.model.Ingredient;

import java.util.Map;

/**
 * Created by Andrey on 12/3/2016.
 */
public interface DishServiceInterface {

        //Gets all the ingredients needed to prepare a Dish
        Map<Ingredient, Double> getIngredients(int id);

        /**
         * @param: id - Dish we want to check
         * @param: number - quantity of dishes
         * */
        boolean validateIngredients(int id, int number);
}
