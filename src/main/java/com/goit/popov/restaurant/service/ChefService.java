package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.model.Chef;
import com.goit.popov.restaurant.model.Employee;
import com.goit.popov.restaurant.model.Position;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Andrey on 11/13/2016.
 */
public class ChefService extends EmployeeService<Chef> {

        private Employee create(String name, Date dob, String phone, BigDecimal salary, Position position) {
                Employee employee = new Chef();
                return setProperties(name, dob, phone, salary, position, employee);
        }
}
