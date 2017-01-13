package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.EmployeeDAO;
import com.goit.popov.restaurant.model.Employee;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by Andrey on 11/13/2016.
 */
@Transactional
public class EmployeeService {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(EmployeeService.class);

        private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        @Autowired
        protected EmployeeDAO employeeDAO;

        public void setEmployeeDAO(EmployeeDAO employeeDAO) {
                this.employeeDAO = employeeDAO;
        }

        public List<Employee> getEmployees() {
                return employeeDAO.getAll();
        }

        public Employee getEmployeeById(int employeeId) {
                return employeeDAO.getById(employeeId);
        }

        public Employee getEmployeeByName(String name) {
                return employeeDAO.getByName(name);
        }

        public Employee getEmployeeByLogin(String login) {
                return employeeDAO.getByLogin(login);
        }

        public void deleteById(int employeeId) {
                employeeDAO.delete(getEmployeeById(employeeId));
        }

        public void save(Employee employee) {
                Employee emp = transform(employee);
                employeeDAO.insert(emp);
                logger.info("Saved employee: "+employee);
        }

        public void update(Employee employee) {
                Employee emp = transform(employee);
                employeeDAO.update(emp);
                logger.info("Updated employee: "+employee);
        }

        public Employee getEmployeeByLoginAndPassword(String login, String password) {
                return employeeDAO.getByLoginAndPassword(login, password);
        }

        /**
         * Use if we need to change Employee->any other (Chef/Waiter/Manager)
         * @param employee
         * @param rewrite
         */
        public void update(Employee employee, boolean rewrite) throws PersistenceException {
                Employee emp = transform(employee);
                if (rewrite) {
                        try {
                                employeeDAO.delete(employee);
                        } catch (PersistenceException e) {
                                throw new PersistenceException("Cannot change the position to regular employee," +
                                        " the employee has references!");
                        }
                        employeeDAO.insert(emp);
                        logger.info("Re-inserted employee: "+emp);
                } else {
                        update(employee);
                }
        }

        /**
         * Transforms object from any type extending Employee to Employee
         * E.g. Waiter - > Cleaner (Employee type) OR Guard (Employee type)
         * @param oldEmployee
         * @return
         */
        private Employee transform(Employee oldEmployee) {
                Employee newEmployee = new Employee();
                newEmployee.setId(oldEmployee.getId());
                newEmployee.setLogin(oldEmployee.getLogin());
                newEmployee.setPassword(passwordEncoder.encode(oldEmployee.getPassword()));
                newEmployee.setName(oldEmployee.getName());
                newEmployee.setDob(oldEmployee.getDob());
                newEmployee.setPhone(oldEmployee.getPhone());
                newEmployee.setPosition(oldEmployee.getPosition());
                newEmployee.setSalary(oldEmployee.getSalary());
                newEmployee.setPhoto(oldEmployee.getPhoto());
                return newEmployee;
        }
}
