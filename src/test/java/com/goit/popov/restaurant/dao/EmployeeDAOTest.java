package com.goit.popov.restaurant.dao;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.EmployeeDAO;
import com.goit.popov.restaurant.model.Employee;
import com.goit.popov.restaurant.model.Position;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.slf4j.LoggerFactory;
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

        private static final Logger logger = (Logger) LoggerFactory.getLogger(EmployeeDAOTest.class);

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

        private Employee actualEmployee;

        private int generatedId;

        @Before
        public void setUp() throws Exception {
                createDependencies();
                logger.info("References inserted: OK");
        }

        @After
        public void tearDown() throws Exception {
                deleteDependencies();
                logger.info("References deleted: OK");
        }

        @Override
        public void test() {

                init();
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

        @Override
        public void init() {
                Assert.assertNotNull(employeeDAO);
                Assert.assertNotNull(expectedEmployee);
                Assert.assertNotNull(expectedPosition);
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
                logger.info("Insert: OK");
        }

        @Override
        public void read() {
                expectedEmployee = employeeDAO.getById(generatedId);
                assertEquals(actualEmployee, expectedEmployee);
                logger.info("Read: OK");
        }

        public void readName() {
                expectedEmployee = employeeDAO.getByName(expectedEmployee.getName());
                assertEquals(actualEmployee, expectedEmployee);
                logger.info("ReadName: OK");
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
                logger.info("Update: OK");
        }
        @Override
        public void readAll() {
                List<Employee> employeeList = employeeDAO.getAll();
                assertNotNull(employeeList.size());
                logger.info("ReadAll: OK");
        }
        @Override
        public void delete() {
                employeeDAO.delete(this.actualEmployee);
                Employee actualEmployee = helper.getByIdEmployee(generatedId);
                assertNull(actualEmployee);
                logger.info("Delete: OK");
        }
}
