package com.goit.popov.restaurant.dao.entity;

import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Menu;

/**
 * MenuDAO interface for CRUD and add/delete operations
 * @Author: Andrey P.
 * @version 1.0
 */
public interface MenuDAO extends GenericDAO<Menu> {

        void addDish(Menu menu, Dish dish);

        void deleteDish(Menu menu, Dish dish);

}
