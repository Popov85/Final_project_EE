package com.goit.popov.restaurant.model.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.goit.popov.restaurant.controller.converters.DishWrapperDeserializer;
import com.goit.popov.restaurant.model.Ingredient;

import java.util.Map;

/**
 * Created by Andrey on 16.02.2017.
 */
@JsonDeserialize(using = DishWrapperDeserializer.class)
public class DishWrapper {
        private Map<Ingredient, Double> ingredients;

        public Map<Ingredient, Double> getIngredients() {
                return ingredients;
        }

        public void setIngredients(Map<Ingredient, Double> ingredients) {
                this.ingredients = ingredients;
        }
}
