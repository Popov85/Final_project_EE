package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.EmployeeDAO;
import com.goit.popov.restaurant.model.Employee;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by Andrey on 11/13/2016.
 */
public class EmployeeService implements StaffService<Employee> {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(EmployeeService.class);

        private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        @Autowired
        protected EmployeeDAO employeeDAO;

        @Override
        public long insert(Employee employee) {
                employee.setPassword(passwordEncoder.encode(employee.getPassword()));
                return employeeDAO.insert(employee);
        }

        @Override
        public void update(Employee employee) {
                employee.setPassword(employee.getPassword().length()>=60 ? employee.getPassword() :
                        passwordEncoder.encode(employee.getPassword()));
                employeeDAO.update(employee);
        }

        @Override
        public void updateThroughDelete(Employee employee) throws PersistenceException {
                try {
                        delete(employee);
                } catch (PersistenceException e) {
                        throw new PersistenceException("Cannot change the position to employee," +
                                " the employee has references!");
                }
                employeeDAO.insert(employee);
                logger.info("Re-inserted employee: "+employee);
        }

        @Override
        public void delete(Employee employee) {
                employeeDAO.delete(employee);
        }

        @Override
        public void deleteById(int id) {
                employeeDAO.delete(getById(id));
        }

        @Override
        public Employee getById(int id) {
                return employeeDAO.getById(id);
        }

        @Override
        public List<Employee> getAll() {
                return employeeDAO.getAll();
        }

        public Employee getEmployeeByLogin(String login) {
                return employeeDAO.getByLogin(login);
        }
}
