package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.MenuDAO;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Menu;
import com.goit.popov.restaurant.service.dataTables.DataTablesListToJSONConvertible;

import java.util.Set;

/**
 * Created by Andrey on 16.02.2017.
 */
public interface MenuService extends MenuDAO, DataTablesListToJSONConvertible<Menu> {

        void updateMenusDishes(Long menuId, Set<Dish> dishes);

        void deleteById(Long id);
}
