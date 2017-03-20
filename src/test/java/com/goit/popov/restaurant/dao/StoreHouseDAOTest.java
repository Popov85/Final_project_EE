package com.goit.popov.restaurant.dao;

import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.StoreHouse;
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

import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Andrey on 20.03.2017.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-context.xml", "/test-data.xml"})
public class StoreHouseDAOTest {
        //private static final double Q_UPD = 10.0d;

        @Autowired
        private SessionFactory sessionFactory;

        @Autowired
        private StoreHouseDAO storeHouseDAO;

        @Autowired
        private UnitDAO unitDAO;

        @Autowired
        private StoreHouse expectedStoreHouse;

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
                storeHouseDAO.insert(expectedStoreHouse);
                sessionFactory.getCurrentSession().flush();
                // READ
                StoreHouse actualStoreHouse = storeHouseDAO.getByIngredient(expectedStoreHouse.getIngredient());
                assertNotNull(actualStoreHouse);
                assertEquals(actualStoreHouse.getIngredient().getId(), expectedStoreHouse.getIngredient().getId());
                // DELETE
                storeHouseDAO.delete(expectedStoreHouse);
                sessionFactory.getCurrentSession().flush();
                StoreHouse emptyStoreHouse = storeHouseDAO.getByIngredient(expectedStoreHouse.getIngredient());
                assertNull(emptyStoreHouse);
        }

}
