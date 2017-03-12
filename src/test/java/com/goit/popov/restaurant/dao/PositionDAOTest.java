package com.goit.popov.restaurant.dao;

import ch.qos.logback.classic.Logger;
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

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Andrey on 27.10.2016.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-context.xml", "/test-data.xml"})
public class PositionDAOTest {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(PositionDAOTest.class);

        private static final String POSITION_UPD = "UpdatedPosition";

        @Autowired
        private SessionFactory sessionFactory;

        @Autowired
        private PositionDAO positionDAO;

        @Autowired
        private RoleDAO roleDAO;

        @Autowired
        private Role expectedRole;

        @Autowired
        private Position expectedPosition;

        private Position actualPosition;

        private Long generatedId;

        @Before
        public void setUp() throws Exception {
                roleDAO.insert(expectedRole);
        }

        @After
        public void tearDown() throws Exception {
                roleDAO.delete(expectedRole);
        }

        @Test
        public void testCRUD() {
                // CREATE
                generatedId = positionDAO.insert(expectedPosition);
                assertNotNull(generatedId);
                // READ
                actualPosition = positionDAO.getById(generatedId);
                assertEquals(actualPosition.getId(), expectedPosition.getId());
                assertEquals(actualPosition.getName(), expectedPosition.getName());
                // UPDATE
                expectedPosition.setName(POSITION_UPD);
                positionDAO.update(expectedPosition);
                Position updatedPosition = positionDAO.getById(generatedId);
                assertEquals(expectedPosition.getId(), updatedPosition.getId());
                assertEquals(expectedPosition.getName(), updatedPosition.getName());
                // READ ALL
                List<Position> positionList = positionDAO.getAll();
                assertNotNull(positionList.size());
                // DELETE
                positionDAO.delete(updatedPosition);
                Position emptyPosition = positionDAO.getById(generatedId);
                assertNull(emptyPosition);
        }

        // Position with null Role
        @Test(expected=PersistenceException.class)
        public void testException() {
                Position position = new Position();
                position.setName("SamplePosition");
                position.setRole(null);
                Long generatedId = positionDAO.insert(position);
                sessionFactory.getCurrentSession().flush();
        }

        // Two positions with equal names
        @Test(expected=ConstraintViolationException.class)
        public void testExceptionTwo() {
                Position position = new Position();
                position.setName("SamplePosition");
                position.setRole(expectedRole);
                positionDAO.insert(position);
                sessionFactory.getCurrentSession().flush();
                Position anotherPosition = new Position();
                position.setName("SamplePosition");
                position.setRole(expectedRole);
                positionDAO.insert(anotherPosition);
                sessionFactory.getCurrentSession().flush();
        }
}
