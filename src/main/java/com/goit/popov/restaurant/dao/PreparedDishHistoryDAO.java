package com.goit.popov.restaurant.dao;

import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.model.PreparedDish;

/**
 * Created by Andrey on 10/14/2016.
 */
public interface PreparedDishHistoryDAO extends GenericDAO<PreparedDish>  {

        long getPreparedDishesQuantity(Order order);
        long getPreparedDishesQuantity(Dish dish, Order order);
        long getCancelledDishesQuantity(Dish dish, Order order);
        long count();
}
