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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Andrey on 10/17/2016.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-context.xml", "/test-data.xml"})
public class EmployeeDAOTest {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(EmployeeDAOTest.class);

        private static final String EMP_LOGIN_UPD = "testUpdated";
        private static final String EMP_PASS_UPD = "gJ56Jd_3";
        private static final String EMP_NAME_UPD = "Mr. Test";
        //private static final Date EMP_DOB_UPD = new Date("1999-11-11");
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
                sessionFactory.getCurrentSession().flush();
                positionDAO.insert(expectedPosition);
                sessionFactory.getCurrentSession().flush();
        }

        @After
        public void tearDown() throws Exception {
                positionDAO.delete(expectedPosition);
                sessionFactory.getCurrentSession().flush();
                roleDAO.delete(expectedRole);
                sessionFactory.getCurrentSession().flush();
        }

        @Test
        public void testCRUD() {
                // CREATE
                generatedId = employeeDAO.insert(expectedEmployee);
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
                //expectedEmployee.setDob(EMP_DOB_UPD);
                expectedEmployee.setPhone(EMP_UPD_PHONE);
                expectedEmployee.setSalary(EMP_UPD_SAL);

                employeeDAO.update(expectedEmployee);

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
                Employee emptyEmployee = employeeDAO.getById(generatedId);
                assertNull(emptyEmployee);
        }
}
