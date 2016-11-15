package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.model.Chef;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Andrey on 11/13/2016.
 */
public class ChefService extends EmployeeService<Chef> {

        @Transactional
        public void save(Chef employee) {
                employeeDAO.insert(employee);
        }

        @Transactional
        public void update(Chef employee) {
                employeeDAO.update(employee);
        }
}
