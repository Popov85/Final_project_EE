package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.entity.EmployeeDAO;
import com.goit.popov.restaurant.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrey on 11/6/2016.
 */
public class EmployeeService {
        @Autowired
        private EmployeeDAO employeeDAO;

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
        public void update(Employee employee) {
                employeeDAO.update(employee);
        }

        @Transactional
        public void save(Employee employee) {
                employeeDAO.insert(employee);
        }
}
