package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.model.Waiter;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by Andrey on 11/13/2016.
 */
public class WaiterService extends EmployeeService<Waiter> {

        @Transactional
        public void save(Waiter employee) {
                employeeDAO.insert(employee);
        }

        @Transactional
        public void update(Waiter employee) {
                employeeDAO.update(employee);
        }

}
