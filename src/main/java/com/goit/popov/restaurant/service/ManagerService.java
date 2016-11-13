package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.model.Manager;

/**
 * Created by Andrey on 11/13/2016.
 */
public class ManagerService extends EmployeeService<Manager> {

        @Override
        void update(Manager employee) {
                employeeDAO.update(employee);
        }

        @Override
        void save(Manager employee) {
                employeeDAO.insert(employee);
        }
}
