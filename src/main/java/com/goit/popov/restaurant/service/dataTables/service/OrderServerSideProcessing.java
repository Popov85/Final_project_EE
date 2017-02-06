package com.goit.popov.restaurant.service.dataTables.service;

import com.goit.popov.restaurant.dao.entity.OrderDAO;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import com.goit.popov.restaurant.service.dataTables.DataTablesServiceServerSideSearch;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Andrey on 2/4/2017.
 */
public class OrderServerSideProcessing extends DataTablesServiceServerSideSearch<Order> {

        @Autowired
        private OrderDAO orderDAO;


        @Override
        protected long count() {
                return orderDAO.count();
        }

        @Override
        protected List<Order> getAllItems(DataTablesInputExtendedDTO dt) {
                return orderDAO.getAll(dt);
        }
}
