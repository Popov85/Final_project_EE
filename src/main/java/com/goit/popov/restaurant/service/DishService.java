package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.entity.DishDAO;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.service.dataTables.DataTablesMapToJSONConvertible;
import com.goit.popov.restaurant.service.dataTables.DataTablesServiceServerSideSearch;

/**
 * Created by Andrey on 12/3/2016.
 */
public abstract class DishService extends DataTablesServiceServerSideSearch<Dish> implements DishDAO,
        DataTablesMapToJSONConvertible<Ingredient, Double>{
}
