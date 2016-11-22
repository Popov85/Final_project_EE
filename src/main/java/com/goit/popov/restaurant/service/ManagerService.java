package com.goit.popov.restaurant.service;
import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.ManagerDAO;
import com.goit.popov.restaurant.model.Employee;
import com.goit.popov.restaurant.model.Manager;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by Andrey on 11/13/2016.
 */
public class ManagerService extends EmployeeService {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(ManagerService.class);

        @Autowired
        protected ManagerDAO managerDAO;

        public void setManagerDAO(ManagerDAO managerDAO) {
                this.managerDAO = managerDAO;
        }
        @Override
        @Transactional
        public void save(Employee employee) {
                Manager manager = transform(employee);
                employeeDAO.insert(manager);
                logger.info("Saved manager: "+manager);
        }
        @Override
        @Transactional
        public void update(Employee employee) {
                Manager manager =transform(employee);
                employeeDAO.update(manager);
                logger.info("Updated manager: "+manager);
        }
        @Override
        @Transactional
        public void update(Employee employee, boolean rewrite) throws Exception {
                Manager manager = transform(employee);
                if (rewrite) {
                        try {
                                employeeDAO.delete(employee);
                        } catch (Exception e) {
                                throw new RuntimeException("Cannot change the position to manager, employee has references!");
                        }
                        employeeDAO.insert(manager);
                        logger.info("Re-inserted chef: "+manager);
                } else {
                        update(manager);
                }
        }

        private Manager transform(Employee employee) {
                Manager manager = new Manager();
                manager.setId(employee.getId());
                manager.setName(employee.getName());
                manager.setDob(employee.getDob());
                manager.setPhone(employee.getPhone());
                manager.setPosition(employee.getPosition());
                manager.setSalary(employee.getSalary());
                return manager;
        }

}
