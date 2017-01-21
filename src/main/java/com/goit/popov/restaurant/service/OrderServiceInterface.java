package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;

import java.util.Map;

/**
 * Created by Andrey on 12/3/2016.
 */
public interface OrderServiceInterface {

        void closeOrder(int orderId);
        boolean validateOrder(Order o);
}
