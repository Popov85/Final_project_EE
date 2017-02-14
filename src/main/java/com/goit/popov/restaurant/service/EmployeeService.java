package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.EmployeeDAO;
import com.goit.popov.restaurant.model.Employee;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

/**
 * Created by Andrey on 11/13/2016.
 */
public class EmployeeService {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(EmployeeService.class);

        @Autowired
        private PasswordEncoder encoder;

        @Autowired
        protected EmployeeDAO employeeDAO;

        public Long insert(Employee employee) {
                employee.setPassword(encoder.encode(employee.getPassword()));
                return employeeDAO.insert(employee);
        }

        public void update(Employee employee) {
                employee.setPassword(employee.getPassword().length()>=60 ? employee.getPassword() :
                        encoder.encode(employee.getPassword()));
                employeeDAO.update(employee);
        }

        public void delete(Employee employee) {
                employeeDAO.delete(employee);
        }

        public void deleteById(Long id) {
                employeeDAO.delete(getById(id));
        }

        public Employee getById(Long id) {
                return employeeDAO.getById(id);
        }

        public List<Employee> getAll() {
                return employeeDAO.getAll();
        }

        public Employee getEmployeeByLogin(String login) {
                return employeeDAO.getByLogin(login);
        }
}
