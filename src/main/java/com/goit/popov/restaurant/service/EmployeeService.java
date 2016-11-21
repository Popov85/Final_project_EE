package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.EmployeeDAO;
import com.goit.popov.restaurant.model.Employee;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrey on 11/13/2016.
 */
public class EmployeeService<T extends Employee> {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(EmployeeService.class);

        @Autowired
        protected EmployeeDAO employeeDAO;

        public void setEmployeeDAO(EmployeeDAO employeeDAO) {
                this.employeeDAO = employeeDAO;
        }

        @Transactional
        public List<Employee> getEmployees() {
                return employeeDAO.getAll();
        }

        @Transactional
        public Employee getEmployeeById(int employeeId) {
                return employeeDAO.getById(employeeId);
        }

        @Transactional
        public void deleteById(int employeeId) {
                employeeDAO.delete(getEmployeeById(employeeId));
        }

        @Transactional
        public void save(Employee employee) {
                employeeDAO.insert(employee);
        }

        @Transactional
        public void update(Employee employee) {
                Employee emp = transform(employee);
                employeeDAO.update(employee);
                logger.info("Updated employee: "+employee);
        }

        /**
         * Use if we need to change Employee->any other (Chef/Waiter/Manager)
         * @param employee
         * @param rewrite
         */
        @Transactional
        public void update(Employee employee, boolean rewrite) throws Exception {
                Employee emp = transform(employee);
                if (rewrite) {
                        try {
                                employeeDAO.delete(employee);
                        } catch (Exception e) {
                                throw new Exception("Cannot change the position, employee has references!");
                        }
                        employeeDAO.insert(emp);
                        logger.info("Re-inserted employee: "+emp);
                }
        }

        /**
         * Transforms object from any type extending Employee to Employee
         * E.g. Waiter - > Cleaner (Employee type) OR Guard (Employee type)
         * @param employee
         * @return
         */
        private Employee transform(Employee employee) {
                Employee emp = new Employee();
                emp.setId(employee.getId());
                emp.setName(employee.getName());
                emp.setDob(employee.getDob());
                emp.setPhone(employee.getPhone());
                emp.setPosition(employee.getPosition());
                emp.setSalary(employee.getSalary());
                return emp;
        }
}
