package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.WaiterDAO;
import com.goit.popov.restaurant.model.Employee;
import com.goit.popov.restaurant.model.Waiter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;


/**
 * Created by Andrey on 11/13/2016.
 */
@Transactional
public class WaiterService implements StaffService<Waiter> {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(WaiterService.class);

        @Autowired
        private WaiterDAO waiterDAO;

        @Override
        public long insert(Employee employee) {
                return waiterDAO.insert(new Waiter(employee));
        }

        @Override
        public void update(Employee employee) {
                waiterDAO.update(new Waiter(employee));
        }

        @Override
        public void updateThroughDelete(Employee employee) throws PersistenceException {
                logger.info("Updating waiter (though delete) class is: "+employee.getClass());
                Waiter waiter = new Waiter(employee);
                try {
                        delete(waiter);
                } catch (PersistenceException e) {
                        throw new PersistenceException("Cannot change the position to waiter," +
                                " the employee has references!");
                }
                waiterDAO.insert(waiter);
                logger.info("Re-inserted waiter: "+waiter);
        }

        @Override
        public void delete(Waiter employee) {
                logger.info("Delete waiter class is: "+employee.getClass());
                waiterDAO.delete(employee);
        }

        @Override
        public void deleteById(int id) {
                waiterDAO.delete(getById(id));
        }

        @Override
        public Waiter getById(int id) {
                return waiterDAO.getById(id);
        }

        @Override
        public List<Waiter> getAll() {
                return waiterDAO.getAll();
        }
}
