package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;

import java.util.Map;

/**
 * Created by Andrey on 12/3/2016.
 */
public interface OrderServiceInterface {

        /**
         * Updates quantity of ingredients once an order is open
         * @param order
         */
        void updateStock(Order order);

        /**
         * Gets all the dishes ordered in this Order
         * @param orderId
         * @return
         */
        Map<Dish, Integer> getDishes(int orderId);

        /**
         *  Decides if there are all the required ingredients available in stock.
         * @param orderId of Order we want to check
         * @return
         */
        boolean validateIngredients(int orderId);
}
