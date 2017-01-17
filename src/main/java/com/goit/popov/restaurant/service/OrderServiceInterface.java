package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;

import java.util.Map;

/**
 * Created by Andrey on 12/3/2016.
 */
public interface OrderServiceInterface {

        void updateStock(Order order);
        Map<Dish, Integer> getDishes(int orderId);
        boolean validateIngredients(Map<Dish, Integer> dishes);
        void closeOrder(int orderId);
        long count();
}
