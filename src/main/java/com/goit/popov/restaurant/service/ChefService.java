package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.model.Chef;

/**
 * Created by Andrey on 11/13/2016.
 */
public class ChefService extends EmployeeService<Chef> {

        void update(Chef employee) {
                employeeDAO.insert(employee);

        }

        void save(Chef employee) {
                employeeDAO.update(employee);
        }
}
