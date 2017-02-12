package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.ChefDAO;
import com.goit.popov.restaurant.model.Chef;
import com.goit.popov.restaurant.model.Employee;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by Andrey on 11/13/2016.
 */
public class ChefService implements StaffService<Chef> {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(ChefService.class);

        @Autowired
        private ChefDAO chefDAO;

        @Override
        public long insert(Employee employee) {
                return chefDAO.insert(new Chef(employee));
        }

        @Override
        public void update(Employee employee) {
                chefDAO.update(new Chef(employee));
        }

        @Override
        public void updateThroughDelete(Employee employee) throws PersistenceException {
                Chef chef = new Chef(employee);
                try {
                        delete(chef);
                } catch (PersistenceException e) {
                        throw new PersistenceException("Cannot change the position to chef," +
                                " the employee has references!");
                }
                chefDAO.insert(chef);
                logger.info("Re-inserted/Updated chef: "+chef);
        }

        @Override
        public void delete(Chef employee) {
                chefDAO.delete(employee);
        }

        @Override
        public void deleteById(int id) {
                chefDAO.delete(getById(id));
        }

        @Override
        public Chef getById(int id) {
                return chefDAO.getById(id);
        }

        @Override
        public List<Chef> getAll() {
                return chefDAO.getAll();
        }
}
