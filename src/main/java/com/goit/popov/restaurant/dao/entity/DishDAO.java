package com.goit.popov.restaurant.dao.entity;

import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import java.util.List;

/**
 * DishDAO interface
 * @Author: Andrey P.
 * @version 1.0
 */
public interface DishDAO extends GenericDAO<Dish> {
        long count();
        // TODO make it a separate interface
        List<Dish> getAllItems(DataTablesInputExtendedDTO dt);
        void deleteById(int id);
}
