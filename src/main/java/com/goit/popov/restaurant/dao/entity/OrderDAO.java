package com.goit.popov.restaurant.dao.entity;

import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.model.PreparedDish;
import com.goit.popov.restaurant.model.Waiter;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOUniversal;

import java.util.List;

/**
 * Created by Andrey on 10/14/2016.
 */
public interface OrderDAO extends GenericDAO<Order> {
        void delete(int id);
        void close(Order order);
        List<Order> getAllClosed();
        List<Order> getAllOpened();

        long count();
        long countWaiter(Waiter waiter);

        List<Order> getAllToday();
        List<Order> getAllWaiterToday(int waiterId);

        List<Order> getAllOrders(DataTablesInputExtendedDTO dt);
        List<Order> getAllWaiterArchive(int waiterId, DataTablesInputExtendedDTO dt);

        List<PreparedDish> getAllWithPreparedDishes();
}
