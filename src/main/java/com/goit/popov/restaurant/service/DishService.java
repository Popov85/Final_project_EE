package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.entity.DishDAO;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.service.dataTables.DataTablesListToJSONConvertible;
import com.goit.popov.restaurant.service.dataTables.DataTablesMapToJSONConvertible;
import com.goit.popov.restaurant.service.dataTables.DataTablesSearchable;

import java.util.Map;

/**
 * Created by Andrey on 12/3/2016.
 */
public interface DishService extends DishDAO, DataTablesMapToJSONConvertible<Ingredient, Double>,
        DataTablesSearchable<Dish> {
}
