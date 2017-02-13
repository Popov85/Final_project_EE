package com.goit.popov.restaurant.service.dataTables.service;

import com.goit.popov.restaurant.dao.DishDAO;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import com.goit.popov.restaurant.service.dataTables.DataTablesServiceServerSideSearch;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Andrey on 2/4/2017.
 */
public class DishServerSideProcessing extends DataTablesServiceServerSideSearch<Dish> {

        @Autowired
        private DishDAO dishDAO;

        @Override
        public long count() {
                return dishDAO.count();
        }

        @Override
        public List<Dish> getAllItems(DataTablesInputExtendedDTO dt) {
                return dishDAO.getAllItems(dt);
        }
}
