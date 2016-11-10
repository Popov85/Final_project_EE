package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.entity.PositionDAO;
import com.goit.popov.restaurant.model.Position;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrey on 10.11.2016.
 */
public class PositionService {
        private PositionDAO positionDAO;

        public void setPositionDAO(PositionDAO positionDAO) {
                this.positionDAO = positionDAO;
        }

        @Transactional
        public List<Position> getAll() {
             return positionDAO.getAll();
        }
}
