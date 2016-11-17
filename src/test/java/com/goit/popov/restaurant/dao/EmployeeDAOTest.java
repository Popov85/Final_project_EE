package com.goit.popov.restaurant.dao;

import com.goit.popov.restaurant.dao.entity.EmployeeDAO;
import com.goit.popov.restaurant.dao.impl.EmployeeDAOImplJPA;
import com.goit.popov.restaurant.model.Employee;
import com.goit.popov.restaurant.model.Position;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Andrey on 10/17/2016.
 */
public class EmployeeDAOTest {

        private ApplicationContext context;

        private static final String EMP_NAME = "Mr. Test";
        private static final String EMP_DOB = "1999-10-10";
        private static final int EMP_POS = 1;
        private static final String EMP_POS_NAME = "Manager";
        private static final BigDecimal EMP_SAL = new BigDecimal(12000);
        private static final String EMP_PHONE = "+380630000000";

        private static final String EMP_NAME_UPD = "Mr. Test";
        private static final String EMP_DOB_UPD = "1999-11-11";
        private static final int EMP_POS_UPD = 2;
        private static final String EMP_POS_NAME_UPD = "Chef";
        private static final String EMP_UPD_PHONE = "+380630101010";
        private static final BigDecimal EMP_UPD_SAL = new BigDecimal(13000);


        private Employee expectedEmployee;

        private Employee actualEmployee;

        private int generatedId;

        private EmployeeDAO employeeDAO;

        private SessionFactory sessionFactory;

        private Helper helper;

        @Before
        public void setUp() throws Exception {
                context = new ClassPathXmlApplicationContext("test-context.xml");
                employeeDAO = (EmployeeDAOImplJPA) context.getBean("employeeDAO");
                sessionFactory = (SessionFactory) context.getBean("sessionFactory");
                helper = (Helper) context.getBean("helper");
                expectedEmployee = createEmployee();
        }

        @Test
        public void test() {
                // Create
                insert();
                // Read by id
                read();
                // Read by Name
                readName();
                // Read all
                readAll();
                // Update
                update();
                // Delete
                delete();
        }

        private void readName() {
                expectedEmployee = employeeDAO.getByName(EMP_NAME);
                assertEquals(actualEmployee, expectedEmployee);
        }

        private Employee createEmployee() {
                Employee employee = new Employee();
                employee.setName(EMP_NAME);
                try {
                        employee.setDob(Helper.format.parse(EMP_DOB));
                } catch (ParseException e) {
                        e.printStackTrace();
                }
                Position position = new Position();
                position.setId(EMP_POS);
                position.setName(EMP_POS_NAME);
                employee.setPosition(position);
                employee.setPhone(EMP_PHONE);
                employee.setSalary(EMP_SAL);
                return employee;
        }

        private void insert() {
                generatedId = employeeDAO.insert(expectedEmployee);
                assertNotNull(generatedId);
                actualEmployee = helper.getByIdEmployee(generatedId);
                assertEquals(expectedEmployee, actualEmployee);
        }

        private void read() {
                expectedEmployee = employeeDAO.getById(generatedId);
                assertEquals(actualEmployee, expectedEmployee);
        }

        private void update() {
                expectedEmployee.setName(EMP_NAME_UPD);
                try {
                        expectedEmployee.setDob(Helper.format.parse(EMP_DOB_UPD));
                } catch (ParseException e) {
                        e.printStackTrace();
                }
                Position position = new Position();
                position.setId(EMP_POS_UPD);
                position.setName(EMP_POS_NAME_UPD);
                expectedEmployee.setPosition(position);
                expectedEmployee.setPhone(EMP_UPD_PHONE);
                expectedEmployee.setSalary(EMP_UPD_SAL);

                employeeDAO.update(expectedEmployee);
                Employee updatedEmployee = helper.getByIdEmployee(generatedId);
                assertEquals(expectedEmployee, updatedEmployee);
        }

        private void readAll() {
                List<Employee> positionList = employeeDAO.getAll();
                assertNotNull(positionList.size());
        }

        private void delete() {
                employeeDAO.delete(this.actualEmployee);
                Position actualPosition = helper.getByIdPosition(generatedId);
                assertNull(actualPosition);
        }
}
