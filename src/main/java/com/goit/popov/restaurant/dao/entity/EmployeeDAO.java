package com.goit.popov.restaurant.dao.entity;

import com.goit.popov.restaurant.model.Employee;

/**
 * EmployeeDAO interface for CRUD operations
 * @Author: Andrey P.
 * @version 1.0
 */
public interface EmployeeDAO extends GenericDAO<Employee> {
        Employee getByName(String name);
        Employee getByLogin(String login);
        Employee getByLoginAndPassword(String login, String password);
}
