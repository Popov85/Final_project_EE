package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.model.Employee;

import java.util.List;

/**
 * Created by Andrey on 11/13/2016.
 */
public interface GenericEmployeeInterface<T extends Employee> {

        List<T> getEmployees();

        T getEmployeeById(int employeeId);

        void deleteById(int employeeId);

        void update(T employee);

        void save(T employee);
}
