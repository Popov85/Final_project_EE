package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.UnitDAO;
import com.goit.popov.restaurant.model.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * Created by Andrey on 11/28/2016.
 */
public class UnitService {

        @Autowired
        private UnitDAO unitDAO;

        public List<Unit> getAll() {
                return unitDAO.getAll();
        }

        public Unit getById(int id) {
                return unitDAO.getById(id);
        }
}
