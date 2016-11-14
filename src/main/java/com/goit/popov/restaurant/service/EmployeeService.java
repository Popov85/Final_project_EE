package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.entity.EmployeeDAO;
import com.goit.popov.restaurant.model.Employee;
import com.goit.popov.restaurant.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Andrey on 11/13/2016.
 */
public class EmployeeService<T extends Employee> {

        @Autowired
        private PositionService positionService;

        @Autowired
        protected EmployeeDAO employeeDAO;

        public void setPositionService(PositionService positionService) {
                this.positionService = positionService;
        }

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
                employeeDAO.update(employee);
        }

        @Transactional
        public void save(String name, Date dob, String phone, BigDecimal salary, String position) {
                employeeDAO.insert(create(name, dob, phone, salary, positionService.getPositionByName(position)));
        }

        @Transactional
        public void update(int id, String name, Date dob, String phone, BigDecimal salary, String position) {
                employeeDAO.update(setId(create(name, dob, phone, salary, positionService.getPositionByName(position)), id));
        }

        private Employee create(String name, Date dob, String phone, BigDecimal salary, Position position) {
                Employee employee = new Employee();
                return setProperties(name, dob, phone, salary, position, employee);
        }

        private Employee setId(Employee employee, int id) {
                employee.setId(id);
                return employee;
        }

        protected Employee setProperties(String name, Date dob, String phone, BigDecimal salary,
                                         Position position, Employee employee) {
                employee.setName(name);
                employee.setDob(dob);
                employee.setPhone(phone);
                employee.setSalary(salary);
                employee.setPosition(position);
                return employee;
        }
}
