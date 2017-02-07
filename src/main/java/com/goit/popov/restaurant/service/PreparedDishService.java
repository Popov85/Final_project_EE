package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.entity.PreparedDishHistoryDAO;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.dataTables.DataTablesListToJSONConvertible;
import com.goit.popov.restaurant.service.dataTables.DataTablesObjectToJSONConvertible;

/**
 * Created by Andrey on 1/29/2017.
 */
public interface PreparedDishService extends PreparedDishHistoryDAO,
        DataTablesListToJSONConvertible<Order>, DataTablesObjectToJSONConvertible<Order> {

        void confirmDishesPrepared(int dishId, int quantity, int orderId, int chefId);
        void confirmDishesCancelled(int dishId, int quantity, int orderId, int chefId);
}
