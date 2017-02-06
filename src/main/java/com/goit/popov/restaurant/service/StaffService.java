package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.model.Employee;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by Andrey on 2/6/2017.
 */
public interface StaffService<T extends Employee> {
        long insert(Employee employee);
        void update(Employee employee);
        void updateThroughDelete(Employee employee) throws PersistenceException;
        void delete(T employee);
        void deleteById(int id);
        T getById(int id);
        List<T> getAll();
}
