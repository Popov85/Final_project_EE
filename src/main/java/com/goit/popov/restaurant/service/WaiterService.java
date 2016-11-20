package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.WaiterDAO;
import com.goit.popov.restaurant.model.Employee;
import com.goit.popov.restaurant.model.Waiter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by Andrey on 11/13/2016.
 */
public class WaiterService extends EmployeeService<Waiter> {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(WaiterService.class);

        @Autowired
        protected WaiterDAO waiterDAO;

        public void setWaiterDAO(WaiterDAO waiterDAO) {
                this.waiterDAO = waiterDAO;
        }

        @Transactional
        public void save(Employee employee) {
                Waiter waiter = transform(employee);
                waiterDAO.insert(waiter);
                logger.info("Saved waiter: "+waiter);
        }

        @Transactional
        public void update(Employee employee) {
                Waiter waiter = transform(employee);
                waiterDAO.update(waiter);
                logger.info("Updated waiter: "+waiter);
                /*if (!employee.getPosition().getName().equals("Waiter")) {
                        employeeDAO.delete(employee);
                        waiterDAO.insert(waiter);
                        logger.info("Re-inserted waiter: "+waiter);
                } else {
                        waiterDAO.update(waiter);
                        logger.info("Updated waiter: "+waiter);
                }*/
        }

        private Waiter transform(Employee employee) {
                Waiter waiter = new Waiter();
                waiter.setId(employee.getId());
                waiter.setName(employee.getName());
                waiter.setDob(employee.getDob());
                waiter.setPhone(employee.getPhone());
                waiter.setPosition(employee.getPosition());
                waiter.setSalary(employee.getSalary());
                return waiter;
        }

}
