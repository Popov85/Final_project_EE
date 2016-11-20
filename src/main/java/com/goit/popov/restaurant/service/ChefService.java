package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.ChefDAO;
import com.goit.popov.restaurant.dao.entity.WaiterDAO;
import com.goit.popov.restaurant.model.Chef;
import com.goit.popov.restaurant.model.Employee;
import com.goit.popov.restaurant.model.Waiter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Andrey on 11/13/2016.
 */
public class ChefService extends EmployeeService<Chef> {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(ChefService.class);

        @Autowired
        protected ChefDAO chefDAO;

        public void setChefDAO(ChefDAO chefDAO) {
                this.chefDAO = chefDAO;
        }

        @Transactional
        public void save(Employee employee) {
                Chef chef = transform(employee);
                chefDAO.insert(chef);
                logger.info("Saved chef: "+chef);
        }

        @Transactional
        public void update(Employee employee) {
                Chef chef = transform(employee);
                chefDAO.update(chef);
                logger.info("Updated chef: "+chef);
        }

        private Chef transform(Employee employee) {
                Chef chef = new Chef();
                chef.setId(employee.getId());
                chef.setName(employee.getName());
                chef.setDob(employee.getDob());
                chef.setPhone(employee.getPhone());
                chef.setPosition(employee.getPosition());
                chef.setSalary(employee.getSalary());
                return chef;
        }
}
