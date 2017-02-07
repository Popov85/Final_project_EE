package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.ManagerDAO;
import com.goit.popov.restaurant.model.Employee;
import com.goit.popov.restaurant.model.Manager;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.PersistenceException;
import java.util.List;


/**
 * Created by Andrey on 11/13/2016.
 */
public class ManagerService implements StaffService<Manager> {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(ManagerService.class);

        @Autowired
        private ManagerDAO managerDAO;

        @Override
        public long insert(Employee employee) {
                return managerDAO.insert(new Manager(employee));
        }

        @Override
        public void update(Employee employee) {
                managerDAO.update(new Manager(employee));
        }

        @Override
        public void updateThroughDelete(Employee employee) throws PersistenceException {
                Manager manager = new Manager(employee);
                try {
                        delete(manager);
                } catch (PersistenceException e) {
                        throw new PersistenceException("Cannot change the position to manager," +
                                " the employee has references!");
                }
                managerDAO.insert(manager);
                logger.info("Re-inserted manager: "+manager);
        }

        @Override
        public void delete(Manager employee) {
                managerDAO.delete(employee);
        }

        @Override
        public void deleteById(int id) {
                managerDAO.delete(getById(id));
        }

        @Override
        public Manager getById(int id) {
                return managerDAO.getById(id);
        }

        @Override
        public List<Manager> getAll() {
                return managerDAO.getAll();
        }
}
