package com.goit.popov.restaurant.dao;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Employee;
import com.goit.popov.restaurant.model.Position;
import com.goit.popov.restaurant.model.Role;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Andrey on 10/17/2016.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-context.xml", "/test-data.xml"})
public class EmployeeDAOTest {

        private static final Logger LOG = (Logger) LoggerFactory.getLogger(EmployeeDAOTest.class);

        private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        private static final String EMP_LOGIN_UPD = "testUpdated";
        private static final String EMP_PASS_UPD = "gJ56Jd_3";
        private static final String EMP_NAME_UPD = "Mr. Test";
        private static final String EMP_IMG_UPD = "img/chef.jpg";
        private static Date EMP_DOB_UPD;

        static {
                try {
                        EMP_DOB_UPD = dateFormat.parse("1999-11-11");
                } catch (ParseException e) {
                        e.printStackTrace();
                }
        }

        private static final String EMP_UPD_PHONE = "+380630101010";
        private static final BigDecimal EMP_UPD_SAL = new BigDecimal(13000);

        @Autowired
        private SessionFactory sessionFactory;

        @Autowired
        private EmployeeDAO employeeDAO;
        @Autowired
        private PositionDAO positionDAO;
        @Autowired
        private RoleDAO roleDAO;
        @Autowired
        private Employee expectedEmployee;
        @Autowired
        private Position expectedPosition;
        @Autowired
        private Role expectedRole;

        private Employee actualEmployee;

        private Long generatedId;

        @Before
        public void setUp() throws Exception {
                roleDAO.insert(expectedRole);
                positionDAO.insert(expectedPosition);
        }

        @After
        public void tearDown() throws Exception {
                positionDAO.delete(expectedPosition);
                roleDAO.delete(expectedRole);
        }

        @Test
        public void itShouldPerformCRUDSmoothly() {
                // CREATE
                generatedId = employeeDAO.insert(expectedEmployee);
                sessionFactory.getCurrentSession().flush();
                assertNotNull(generatedId);
                // READ
                actualEmployee = employeeDAO.getById(generatedId);
                assertEquals(actualEmployee.getId(), expectedEmployee.getId());
                assertEquals(actualEmployee.getName(), expectedEmployee.getName());
                assertEquals(actualEmployee.getLogin(), expectedEmployee.getLogin());
                assertEquals(actualEmployee.getPassword(), expectedEmployee.getPassword());
                assertEquals(actualEmployee.getDob(), expectedEmployee.getDob());
                assertEquals(actualEmployee.getPhone(), expectedEmployee.getPhone());
                assertEquals(actualEmployee.getSalary(), expectedEmployee.getSalary());
                // UPDATE
                expectedEmployee.setLogin(EMP_LOGIN_UPD);
                expectedEmployee.setPassword(EMP_PASS_UPD);
                expectedEmployee.setName(EMP_NAME_UPD);
                expectedEmployee.setDob(EMP_DOB_UPD);
                expectedEmployee.setPhone(EMP_UPD_PHONE);
                expectedEmployee.setSalary(EMP_UPD_SAL);
                byte[] photo = new byte[0];
                try {
                        File fi = new File(EMP_IMG_UPD );
                        photo = Files.readAllBytes(fi.toPath());
                } catch (IOException e) {
                        LOG.error("ERROR: "+e.getMessage());
                }
                expectedEmployee.setPhoto(photo);

                employeeDAO.update(expectedEmployee);
                sessionFactory.getCurrentSession().flush();

                Employee updatedEmployee = employeeDAO.getById(generatedId);
                assertEquals(expectedEmployee.getId(), updatedEmployee.getId());
                assertEquals(expectedEmployee.getName(), updatedEmployee.getName());
                assertEquals(updatedEmployee.getLogin(), expectedEmployee.getLogin());
                assertEquals(updatedEmployee.getPassword(), expectedEmployee.getPassword());
                assertEquals(updatedEmployee.getDob(), expectedEmployee.getDob());
                assertEquals(updatedEmployee.getPhone(), expectedEmployee.getPhone());
                assertEquals(updatedEmployee.getSalary(), expectedEmployee.getSalary());
                // READ ALL
                List<Employee> employeeList = employeeDAO.getAll();
                assertNotNull(employeeList.size());
                // DELETE
                employeeDAO.delete(expectedEmployee);
                sessionFactory.getCurrentSession().flush();
                Employee emptyEmployee = employeeDAO.getById(generatedId);
                assertNull(emptyEmployee);
        }

        @Test
        public void itShouldFindEmployeeByLogin() {
                // CREATE
                expectedEmployee.setLogin("MyLogin");
                generatedId = employeeDAO.insert(expectedEmployee);
                sessionFactory.getCurrentSession().flush();
                // READ
                actualEmployee = employeeDAO.getByLogin("MyLogin");
                assertEquals(actualEmployee.getId(), expectedEmployee.getId());
                assertEquals(actualEmployee.getName(), expectedEmployee.getName());
                assertEquals(actualEmployee.getLogin(), expectedEmployee.getLogin());
                assertEquals(actualEmployee.getPassword(), expectedEmployee.getPassword());
                assertEquals(actualEmployee.getDob(), expectedEmployee.getDob());
                assertEquals(actualEmployee.getPhone(), expectedEmployee.getPhone());
                assertEquals(actualEmployee.getSalary(), expectedEmployee.getSalary());
                // DELETE
                employeeDAO.delete(expectedEmployee);
                sessionFactory.getCurrentSession().flush();
                Employee emptyEmployee = employeeDAO.getById(generatedId);
                assertNull(emptyEmployee);
        }

        @Test(expected = javax.validation.ConstraintViolationException.class)
        public void itShouldNotInsertEmptyEmployee() {
                Employee wrongEmployee = new Employee();
                employeeDAO.insert(wrongEmployee);
                sessionFactory.getCurrentSession().flush();
        }
}
