package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Employee;
import com.goit.popov.restaurant.model.Waiter;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * Created by Andrey on 11/13/2016.
 */
@Transactional
public class WaiterService extends EmployeeService {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(WaiterService.class);

        @Override
        public void save(Employee employee) {
                Waiter waiter = transform(employee);
                employeeDAO.insert(waiter);
                logger.info("Saved waiter: "+waiter);
        }

        @Override
        public void update(Employee employee) {
                Waiter waiter = transform(employee);
                employeeDAO.update(waiter);
                logger.info("Updated waiter: "+employee);
        }

        @Override
        public void update(Employee employee, boolean rewrite) throws Exception {
                Waiter waiter = transform(employee);
                if (rewrite) {
                        try {
                                employeeDAO.delete(employee);
                        } catch (Exception e) {
                                throw new RuntimeException("Cannot change the position to waiter, employee has references!");
                        }
                        employeeDAO.insert(waiter);
                        logger.info("Re-inserted waiter: "+waiter);
                } else {
                        update(waiter);
                }
        }

        private Waiter transform(Employee employee) {
                Waiter waiter = new Waiter();
                waiter.setId(employee.getId());
                waiter.setLogin(employee.getLogin());
                waiter.setPassword(employee.getPassword());
                waiter.setName(employee.getName());
                waiter.setDob(employee.getDob());
                waiter.setPhone(employee.getPhone());
                waiter.setPosition(employee.getPosition());
                waiter.setSalary(employee.getSalary());
                waiter.setPhoto(employee.getPhoto());
                return waiter;
        }
}
