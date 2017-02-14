package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.OrderDAO;
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

        /**
         * Closed order means that money for it has been already obtained
         * @param order
         */
        void closeOrder(Long order);

        /**
         * Cancelled Order means it will not be paid
         * Ingredients for it may be returned if possible
         * @param id
         */
        void cancelOrder(Long id);

        /**
         * Processing includes validation ingredients
         * Ingredients are decreased after making order
         * @param order
         * @throws NotEnoughIngredientsException
         */
        void processOrder(Order order) throws NotEnoughIngredientsException;

        /**
         * Convenience method to get all existing tables
         * @return
         */
        Integer[] getTables();

        void deleteById(Long id);

        /**
         * DataTables server-side processing
         * @param dt
         * @return
         */
        DataTablesOutputDTOUniversal<Order> getAllOrders(DataTablesInputExtendedDTO dt);
        DataTablesOutputDTOUniversal<Order> getAllOrdersByWaiter(DataTablesInputExtendedDTO dt, Long waiterId);
}
