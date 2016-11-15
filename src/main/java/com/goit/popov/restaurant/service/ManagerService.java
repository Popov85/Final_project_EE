package com.goit.popov.restaurant.service;
import com.goit.popov.restaurant.model.Manager;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by Andrey on 11/13/2016.
 */
public class ManagerService extends EmployeeService<Manager> {

        @Transactional
        public void save(Manager employee) {
                employeeDAO.insert(employee);
        }

        @Transactional
        public void update(Manager employee) {
                employeeDAO.update(employee);
        }

}
