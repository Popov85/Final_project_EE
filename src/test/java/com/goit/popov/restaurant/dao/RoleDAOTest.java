package com.goit.popov.restaurant.dao;

import com.goit.popov.restaurant.model.Role;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
* Created by Andrey on 3/12/2017.
 * @See https://www.youtube.com/watch?v=LYVJ69h76nw
 * @See https://www.marcobehler.com/2014/06/25/should-my-tests-be-transactional/
*/
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-context.xml", "/test-data.xml"})
public class RoleDAOTest {

        private static final String ROLE_UPD = "ROLE_UPDATED_TEST";

        @Autowired
        private SessionFactory sessionFactory;

        @Autowired
        private Role expectedRole;

        @Autowired
        private RoleDAO roleDAO;

        @Test
        public void testCRUD() {
                // CREATE
                Long generatedId = roleDAO.insert(expectedRole);
                assertNotNull(generatedId);
                // READ
                Role actualRole = roleDAO.getById(generatedId);
                assertEquals(actualRole.getId(), expectedRole.getId());
                assertEquals(actualRole.getName(), expectedRole.getName());
                // UPDATE
                expectedRole.setName(ROLE_UPD);
                roleDAO.update(expectedRole);
                Role updatedRole = roleDAO.getById(generatedId);
                assertEquals(expectedRole.getId(), updatedRole.getId());
                assertEquals(expectedRole.getName(), updatedRole.getName());
                // READ ALL
                List<Role> positionList = roleDAO.getAll();
                assertNotNull(positionList.size());
                // DELETE
                roleDAO.delete(updatedRole);
                Role emptyRole = roleDAO.getById(generatedId);
                assertNull(emptyRole);
        }

        // Role with null name
        @Test(expected=ConstraintViolationException.class)
        public void testException() {
                Role role = new Role();
                Long generatedId = roleDAO.insert(role);
                sessionFactory.getCurrentSession().flush();
        }
}
