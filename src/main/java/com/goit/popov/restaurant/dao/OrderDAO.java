package com.goit.popov.restaurant.dao;

import com.goit.popov.restaurant.model.Employee;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import java.util.List;

/**
 * Created by Andrey on 10/14/2016.
 */
public interface OrderDAO extends GenericDAO<Order> {

        Long count();
        Long countWaiter(Employee waiter);

        /**
         * For Chefs
         * @return
         */
        List<Order> getAllToday();

        /**
         * For Waiter
         * @param waiterId
         * @return
         */
        List<Order> getAllWaiterToday(Long waiterId);

        /**
         * DataTables server-side processing
         * @param dt
         * @return
         */
        List<Order> getAll(DataTablesInputExtendedDTO dt);
        List<Order> getAllOrdersByWaiter(DataTablesInputExtendedDTO dt, String[] params);
}
