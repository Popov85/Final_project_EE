package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.PreparedDishHistoryDAO;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.dataTables.DataTablesListToJSONConvertible;
import com.goit.popov.restaurant.service.dataTables.DataTablesObjectToJSONConvertible;

/**
 * Created by Andrey on 1/29/2017.
 */
public interface PreparedDishService extends PreparedDishHistoryDAO,
        DataTablesListToJSONConvertible<Order>, DataTablesObjectToJSONConvertible<Order> {

        void confirmDishesPrepared(Long dishId, Integer quantity, Long orderId, Long chefId);
        void confirmDishesCancelled(Long dishId, Integer quantity, Long orderId, Long chefId);
}
