package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.entity.EmployeeDAO;
import com.goit.popov.restaurant.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrey on 11/13/2016.
 */
public class EmployeeService<T extends Employee> {

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

        public EmployeeService create(String position) {
                EmployeeService employee;
                switch (position) {
                        case "1":
                                employee = new ManagerService();
                                break;
                        case "2":
                                employee = new ChefService();
                                break;
                        case "3":
                                employee = new WaiterService();
                                break;
                        default:
                                throw new RuntimeException();
                }
                return employee;
        }

        @Transactional
        public void save(Employee employee) {
              employeeDAO.insert(employee);
        }

        @Transactional
        public void update(Employee employee) {
                employeeDAO.update(employee);
        }
}
