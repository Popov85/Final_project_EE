package com.goit.popov.restaurant.dao;

import com.goit.popov.restaurant.dao.entity.EmployeeDAO;
import com.goit.popov.restaurant.model.Employee;
import com.goit.popov.restaurant.model.Position;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Andrey on 10/17/2016.
 */
@Transactional
public class EmployeeDAOTest extends AbstractDAOTest {

        private static final String EMP_NAME_UPD = "Mr. Test";
        private static final String EMP_DOB_UPD = "1999-11-11";
        private static final String EMP_UPD_PHONE = "+380630101010";
        private static final BigDecimal EMP_UPD_SAL = new BigDecimal(13000);

        @Autowired
        private Helper helper;
        @Autowired
        private EmployeeDAO employeeDAO;
        @Autowired
        private Employee expectedEmployee;
        @Autowired
        private Position expectedPosition;

        public void setExpectedPosition(Position expectedPosition) {
                this.expectedPosition = expectedPosition;
        }

        public void setExpectedEmployee(Employee expectedEmployee) {
                this.expectedEmployee = expectedEmployee;
        }

        public void setEmployeeDAO(EmployeeDAO employeeDAO) {
                this.employeeDAO = employeeDAO;
        }

        public void setHelper(Helper helper) {
                this.helper = helper;
        }

        private Employee actualEmployee;

        private int generatedId;

        @Before
        public void setUp() throws Exception {
                createDependencies();
        }

        @After
        public void tearDown() throws Exception {
                deleteDependencies();
        }

        @Override
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

        private void createDependencies() {
                helper.insertPosition(expectedPosition);
        }

        private void deleteDependencies() {
                helper.deletePosition(expectedPosition);
        }
        @Override
        public void insert() {
                generatedId = employeeDAO.insert(expectedEmployee);
                assertNotNull(generatedId);
                actualEmployee = helper.getByIdEmployee(generatedId);
                assertEquals(expectedEmployee, actualEmployee);
        }
        @Override
        public void read() {
                expectedEmployee = employeeDAO.getById(generatedId);
                assertEquals(actualEmployee, expectedEmployee);
        }

        public void readName() {
                expectedEmployee = employeeDAO.getByName(expectedEmployee.getName());
                assertEquals(actualEmployee, expectedEmployee);
        }
        @Override
        public void update() {
                expectedEmployee.setName(EMP_NAME_UPD);
                try {
                        expectedEmployee.setDob(Helper.format.parse(EMP_DOB_UPD));
                } catch (ParseException e) {
                        e.printStackTrace();
                }
                expectedEmployee.setPhone(EMP_UPD_PHONE);
                expectedEmployee.setSalary(EMP_UPD_SAL);
                employeeDAO.update(expectedEmployee);
                Employee updatedEmployee = helper.getByIdEmployee(generatedId);
                assertEquals(expectedEmployee, updatedEmployee);
        }
        @Override
        public void readAll() {
                List<Employee> employeeList = employeeDAO.getAll();
                assertNotNull(employeeList.size());
        }
        @Override
        public void delete() {
                employeeDAO.delete(this.actualEmployee);
                Employee actualEmployee = helper.getByIdEmployee(generatedId);
                assertNull(actualEmployee);
        }
}
