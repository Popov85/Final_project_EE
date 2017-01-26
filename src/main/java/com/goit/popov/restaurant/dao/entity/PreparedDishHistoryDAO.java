package com.goit.popov.restaurant.dao.entity;

import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.model.PreparedDish;
import java.util.List;
import java.util.Set;

/**
 * Created by Andrey on 10/14/2016.
 */
public interface PreparedDishHistoryDAO extends GenericDAO<PreparedDish>  {
        int addPreparedDish(PreparedDish dish);
        List<PreparedDish> getAll();
        List<Dish> getAll(Order order);
        List<PreparedDish> getAllPreparedDish(Order order);
        List<PreparedDish> getAllChefToday(int chefId);
        long getPreparedDishesQuantity(Order order);
        long getPreparedDishesQuantity(Dish dish, Order order);
        List<Order> getAllOrderForChef();
        void confirmDishesPrepared(Set<PreparedDish> preparedDishes);
        long count();
}
