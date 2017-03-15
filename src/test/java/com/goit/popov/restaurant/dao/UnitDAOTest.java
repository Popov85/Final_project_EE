package com.goit.popov.restaurant.dao;

import com.goit.popov.restaurant.model.Unit;
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
 * Created by Andrey on 14.03.2017.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-context.xml", "/test-data.xml"})
public class UnitDAOTest {

        private static final String UNIT_UPD = "UNIT_UPD";

        @Autowired
        private SessionFactory sessionFactory;

        @Autowired
        private Unit expectedUnit;

        @Autowired
        private UnitDAO unitDAO;

        @Test
        public void itShouldPerformCRUDSmoothly() {
                // CREATE
                Long generatedId = unitDAO.insert(expectedUnit);
                sessionFactory.getCurrentSession().flush();
                assertNotNull(generatedId);
                // READ
                Unit actualUnit = unitDAO.getById(generatedId);
                assertEquals(actualUnit.getId(), expectedUnit.getId());
                assertEquals(actualUnit.getName(), expectedUnit.getName());
                // UPDATE
                expectedUnit.setName(UNIT_UPD);
                unitDAO.update(expectedUnit);
                sessionFactory.getCurrentSession().flush();
                Unit updatedUnit = unitDAO.getById(generatedId);
                assertEquals(expectedUnit.getId(), updatedUnit.getId());
                assertEquals(expectedUnit.getName(), updatedUnit.getName());
                // READ ALL
                List<Unit> unitList = unitDAO.getAll();
                assertNotNull(unitList.size());
                // DELETE
                unitDAO.delete(updatedUnit);
                sessionFactory.getCurrentSession().flush();
                Unit emptyUnit = unitDAO.getById(generatedId);
                assertNull(emptyUnit);
        }

        @Test(expected=ConstraintViolationException.class)
        public void itShouldNotInsertEmptyUnit() {
                Unit unit = new Unit();
                Long generatedId = unitDAO.insert(unit);
                sessionFactory.getCurrentSession().flush();
        }
}
