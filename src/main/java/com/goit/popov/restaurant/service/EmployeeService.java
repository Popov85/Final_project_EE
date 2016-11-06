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

        private EmployeeDAO employeeDAO;

        @Autowired
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
}
