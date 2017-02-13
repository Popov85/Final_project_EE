package com.goit.popov.restaurant.service.dataTables.service;

import com.goit.popov.restaurant.dao.StoreHouseDAO;
import com.goit.popov.restaurant.model.StoreHouse;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import com.goit.popov.restaurant.service.dataTables.DataTablesServiceServerSideSearch;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Andrey on 2/4/2017.
 */
public class StockServerSideProcessing extends DataTablesServiceServerSideSearch<StoreHouse> {

        @Autowired
        private StoreHouseDAO storeHouseDAO;

        @Override
        protected long count() {
                return storeHouseDAO.count();
        }

        @Override
        protected List<StoreHouse> getAllItems(DataTablesInputExtendedDTO dt) {
                return storeHouseDAO.getAllItems(dt);
        }
}
