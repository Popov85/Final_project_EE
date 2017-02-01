package com.goit.popov.restaurant.dao.entity;

import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;

import java.util.List;
import java.util.Map;

/**
 * DishDAO interface
 * @Author: Andrey P.
 * @version 1.0
 */
public interface DishDAO extends GenericDAO<Dish> {
        long count();
        List<Dish> getAllDishes(DataTablesInputExtendedDTO dt);
}
