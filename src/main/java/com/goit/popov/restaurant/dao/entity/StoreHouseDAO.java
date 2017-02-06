package com.goit.popov.restaurant.dao.entity;

import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.StoreHouse;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;

import java.util.List;

/**
 * StoreHouseDAO interface for basic CRUD operations
 * @Author: Andrey P.
 * @version 1.0
 */
public interface StoreHouseDAO extends GenericDAO<StoreHouse> {

        List<StoreHouse> getAllRunOut(double threshold);

        StoreHouse getByIngredient(Ingredient ingredient);

        Double getQuantityByIngredient(Ingredient ingredient);

        long count();

        List<StoreHouse> getAllItems(DataTablesInputExtendedDTO dt);

}
