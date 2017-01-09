package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Chef;
import com.goit.popov.restaurant.model.Employee;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Andrey on 11/13/2016.
 */
public class ChefService extends EmployeeService {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(ChefService.class);

        private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        @Override
        @Transactional
        public void save(Employee employee) {
                Chef chef = transform(employee);
                employeeDAO.insert(chef);
                logger.info("Saved chef: "+chef);
        }
        @Override
        @Transactional
        public void update(Employee employee) {
                Chef chef = transform(employee);
                employeeDAO.update(chef);
                logger.info("Updated chef: "+chef);
        }
        @Override
        @Transactional
        public void update(Employee employee, boolean rewrite) throws Exception {
                Chef chef = transform(employee);
                if (rewrite) {
                        try {
                                employeeDAO.delete(employee);
                        } catch (Exception e) {
                                throw new RuntimeException("Cannot change the position to chef, employee has references!");
                        }
                        employeeDAO.insert(chef);
                        logger.info("Re-inserted chef: "+chef);
                } else {
                        update(chef);
                }
        }

        private Chef transform(Employee employee) {
                Chef chef = new Chef();
                chef.setId(employee.getId());
                chef.setLogin(employee.getLogin());
                chef.setPassword(passwordEncoder.encode(employee.getPassword()));
                chef.setName(employee.getName());
                chef.setDob(employee.getDob());
                chef.setPhone(employee.getPhone());
                chef.setPosition(employee.getPosition());
                chef.setSalary(employee.getSalary());
                chef.setPhoto(employee.getPhoto());
                return chef;
        }
}
