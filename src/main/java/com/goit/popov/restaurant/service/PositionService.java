package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.entity.PositionDAO;
import com.goit.popov.restaurant.model.Position;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrey on 10.11.2016.
 */
@Transactional
public class PositionService {

        private PositionDAO positionDAO;

        public void setPositionDAO(PositionDAO positionDAO) {
                this.positionDAO = positionDAO;
        }

        public List<Position> getAll() {
             return positionDAO.getAll();
        }

        public Position getById(int id) {
                return positionDAO.getById(id);
        }

        public Position getPositionByName(String name) {
                return positionDAO.getPositionByName(name);
        }

        public void save(Position position) {
                positionDAO.insert(position);
        }

        public void update(Position position) {
                positionDAO.update(position);
        }

        public void delete(Position position) {
                positionDAO.delete(position);
        }

        public void deleteById(int id) {
                Position position = new Position();
                position.setId(id);
                delete(position);
        }

}
