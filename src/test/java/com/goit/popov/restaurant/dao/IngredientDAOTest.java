package com.goit.popov.restaurant.dao;

import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.Unit;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
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
 * Created by Andrey on 18.11.2016.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-context.xml", "/test-data.xml"})
public class IngredientDAOTest {
        private static final String ING_UPD = "Ing_upd";

        @Autowired
        private SessionFactory sessionFactory;

        @Autowired
        private IngredientDAO ingredientDAO;

        @Autowired
        private UnitDAO unitDAO;

        @Autowired
        private Ingredient expectedIngredient;

        @Autowired
        private Unit expectedUnit;

        @Before
        public void setUp() throws Exception {
                unitDAO.insert(expectedUnit);
        }

        @After
        public void tearDown() throws Exception {
                unitDAO.delete(expectedUnit);
        }

        @Test
        public void itShouldPerformCRUDSmoothly() {
                // CREATE
                Long generatedId = ingredientDAO.insert(expectedIngredient);
                sessionFactory.getCurrentSession().flush();
                assertNotNull(generatedId);
                // READ
                Ingredient actualIngredient = ingredientDAO.getById(generatedId);
                assertEquals(actualIngredient.getId(), expectedIngredient.getId());
                assertEquals(actualIngredient.getName(), expectedIngredient.getName());
                // UPDATE
                expectedIngredient.setName(ING_UPD);
                ingredientDAO.update(expectedIngredient);
                sessionFactory.getCurrentSession().flush();
                Ingredient updatedUnit = ingredientDAO.getById(generatedId);
                assertEquals(expectedIngredient.getId(), updatedUnit.getId());
                assertEquals(expectedIngredient.getName(), updatedUnit.getName());
                // READ ALL
                List<Ingredient> ingredientList = ingredientDAO.getAll();
                assertNotNull(ingredientList.size());
                // DELETE
                ingredientDAO.delete(updatedUnit);
                sessionFactory.getCurrentSession().flush();
                Ingredient emptyIngredient = ingredientDAO.getById(generatedId);
                assertNull(emptyIngredient);
        }

        @Test(expected=ConstraintViolationException.class)
        public void itShouldNotInsertEmptyUnit() {
                Ingredient ingredient = new Ingredient();
                ingredientDAO.insert(ingredient);
                sessionFactory.getCurrentSession().flush();
        }

}
