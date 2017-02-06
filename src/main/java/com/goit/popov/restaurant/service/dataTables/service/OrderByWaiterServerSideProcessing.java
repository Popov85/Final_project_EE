package com.goit.popov.restaurant.service.dataTables.service;

import com.goit.popov.restaurant.dao.entity.OrderDAO;
import com.goit.popov.restaurant.dao.entity.WaiterDAO;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.model.Waiter;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import com.goit.popov.restaurant.service.dataTables.DataTablesServiceServerSideSearchUniversal;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Andrey on 2/4/2017.
 */
public class OrderByWaiterServerSideProcessing extends DataTablesServiceServerSideSearchUniversal<Order> {

        @Autowired
        private OrderDAO orderDAO;

        @Autowired
        private WaiterDAO waiterDAO;

        @Override
        protected long count(String[] params) {
                Waiter waiter = waiterDAO.getById(Integer.parseInt(params[0]));
                return orderDAO.countWaiter(waiter);
        }

        @Override
        protected List<Order> getAllItems(DataTablesInputExtendedDTO dt, String[] params) {
                return orderDAO.getAllOrdersByWaiter(dt, params);
        }
}
