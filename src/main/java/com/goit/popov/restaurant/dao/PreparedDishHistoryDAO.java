package com.goit.popov.restaurant.dao;

import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.model.PreparedDish;

/**
 * Created by Andrey on 10/14/2016.
 */
public interface PreparedDishHistoryDAO extends GenericDAO<PreparedDish>  {

        Long getPreparedDishesQuantity(Order order);
        Long getPreparedDishesQuantity(Dish dish, Order order);
        Long getCancelledDishesQuantity(Dish dish, Order order);
        Long count();
}
