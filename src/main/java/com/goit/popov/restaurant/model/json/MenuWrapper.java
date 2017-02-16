package com.goit.popov.restaurant.model.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.goit.popov.restaurant.controller.converters.MenuWrapperDeserializer;
import com.goit.popov.restaurant.model.Dish;

import java.util.Set;

/**
 * Created by Andrey on 16.02.2017.
 */
@JsonDeserialize(using = MenuWrapperDeserializer.class)
public class MenuWrapper {
        private Set<Dish> dishes;

        public Set<Dish> getDishes() {
                return dishes;
        }

        public void setDishes(Set<Dish> dishes) {
                this.dishes = dishes;
        }
}
