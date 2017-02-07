package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.entity.OrderDAO;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import com.goit.popov.restaurant.service.dataTables.DataTablesMapToJSONConvertible;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOUniversal;
import com.goit.popov.restaurant.service.exceptions.NotEnoughIngredientsException;

/**
 * Created by Andrey on 1/23/2017.
 */
public interface OrderService extends OrderDAO, DataTablesMapToJSONConvertible<Dish, Integer> {

        void closeOrder(int orderId);
        void processOrder(Order order) throws NotEnoughIngredientsException;
        Integer[] getTables();
        void cancelOrder(int id);

        DataTablesOutputDTOUniversal<Order> getAllOrders(DataTablesInputExtendedDTO dt);
        DataTablesOutputDTOUniversal<Order> getAllOrdersByWaiter(DataTablesInputExtendedDTO dt, int waiterId);
}
