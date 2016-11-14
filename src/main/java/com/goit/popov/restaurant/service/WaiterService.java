package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.model.Employee;
import com.goit.popov.restaurant.model.Position;
import com.goit.popov.restaurant.model.Waiter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Andrey on 11/13/2016.
 */
public class WaiterService extends EmployeeService<Waiter> {

        private Employee create(String name, Date dob, String phone, BigDecimal salary, Position position) {
                Employee employee = new Waiter();
                System.out.println("Create waiter....");
                return setProperties(name, dob, phone, salary, position, employee);
        }
}
