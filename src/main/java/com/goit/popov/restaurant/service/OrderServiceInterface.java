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
         * Gets all the dishes included in this Order
         * @param orderId
         * @return
         */
        Map<Dish, Integer> getDishes(int orderId);

        /**
         *  Decides if there are all the required ingredients available in stock.
         * @param dishes Map of potential Order's dishes we want to check
         * @return
         */
        boolean validateIngredients(Map<Dish, Integer> dishes);

        /**
         * Closes an opened earlier order as soon as a client checks out
         * @param orderId
         */
        void closeOrder(int orderId);

        /**
         * Counts the number of orders
         * @return the total number of orders
         */
        long count();
}
