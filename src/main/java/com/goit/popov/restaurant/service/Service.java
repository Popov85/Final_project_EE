package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Ingredient;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrey on 1/28/2017.
 */
public class Service {

        public Map<Ingredient, Double> getIngredients(Map<Dish, Integer> dishes) {
                Map<Ingredient, Double> ingredients = new HashMap<>();
                for (Map.Entry<Dish, Integer> dish : dishes.entrySet()) {
                        Map<Ingredient, Double> dishIngredients = dish.getKey().getIngredients();
                        Integer requiredQuantity = dish.getValue();
                        for (Map.Entry<Ingredient, Double> dishIngredient : dishIngredients.entrySet()) {
                                Ingredient nextIngredient = dishIngredient.getKey();
                                if (!ingredients.containsKey(nextIngredient)) {
                                        ingredients.put(nextIngredient,
                                                dishIngredient.getValue()*requiredQuantity);
                                } else {
                                        Double currentValue = ingredients.get(nextIngredient).doubleValue();
                                        Double newValue = currentValue + dishIngredient.getValue()*requiredQuantity;
                                        ingredients.put(nextIngredient,
                                                dishIngredient.getValue()*requiredQuantity);
                                }
                        }
                }
                return ingredients;
        }
}
