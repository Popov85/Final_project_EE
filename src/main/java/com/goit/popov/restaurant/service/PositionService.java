package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.PositionDAO;
import com.goit.popov.restaurant.model.Position;
import com.goit.popov.restaurant.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * Created by Andrey on 10.11.2016.
 */
public class PositionService {

        @Autowired
        private PositionDAO positionDAO;

        @Autowired
        private Role defaultRole;

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
                position.setRole(defaultRole);
                positionDAO.insert(position);
        }

        public void update(Position position) {
                position.setRole(defaultRole);
                positionDAO.update(position);
        }

        public void delete(Position position) {
                positionDAO.delete(position);
        }

        public void deleteById(int id) {
                delete(getById(id));
        }

        public boolean isPossibleOperation(int id) {
                return (id!=1 && id!=2 && id!=3);
        }
}
