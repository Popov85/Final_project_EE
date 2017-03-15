package com.goit.popov.restaurant.dao;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.Unit;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Andrey on 18.11.2016.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-context.xml", "/test-data.xml"})
public class DishDAOTest {

        /*
        private static final Logger logger = (Logger) LoggerFactory.getLogger(DishDAOTest.class);

        private static final String DISH_NAME_UPD = "UpdatedName";
        private static final String DISH_CAT_UPD = "UpdatedCategory";
        private static final double DISH_WEIGHT_UPD = 0.245d;
        private static final BigDecimal DISH_PRICE_UPD = new BigDecimal(20);

        @Autowired
        private Helper helper;
        @Autowired
        private DishDAO dishDAO;
        @Autowired
        public Dish expectedDish1;
        @Autowired
        private Ingredient expectedIngredient1;
        @Autowired
        private Ingredient expectedIngredient2;
        @Autowired
        private Unit expectedUnit;

        private Dish actualDish;

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
        */













        /*
        private void createDependencies() {
                helper.insertUnit(expectedUnit);
                helper.insertIngredient(expectedIngredient1);
                helper.insertIngredient(expectedIngredient2);
        }

        private void deleteDependencies() {
                helper.deleteIngredient(expectedIngredient1);
                helper.deleteIngredient(expectedIngredient2);
                helper.deleteUnit(expectedUnit);
        }

        @Override
        protected void init() {

        }

        @Override
        protected void insert() {
                generatedId = dishDAO.insert(expectedDish1);
                assertNotNull(generatedId);
                actualDish = helper.getByIdDish(generatedId);
                assertEquals(expectedDish1, actualDish);
                logger.info("Insert: OK");
        }

        @Override
        protected void read() {
                Dish expectedDish = dishDAO.getById(generatedId);
                assertEquals(actualDish, expectedDish);
                logger.info("Read: OK");
        }

        @Override
        protected void readAll() {
                List<Dish> dishList = dishDAO.getAll();
                assertNotNull(dishList.size());
                logger.info("ReadAll: OK");
        }

        @Override
        protected void update() {
                expectedDish1.setName(DISH_NAME_UPD);
                expectedDish1.setCategory(DISH_CAT_UPD);
                expectedDish1.setWeight(DISH_WEIGHT_UPD);
                expectedDish1.setPrice(DISH_PRICE_UPD);
                dishDAO.update(expectedDish1);
                Dish updatedDish = helper.getByIdDish(generatedId);
                assertEquals(expectedDish1, updatedDish);
                logger.info("Update: OK");
        }

        @Override
        protected void delete() {
                dishDAO.delete(actualDish);
                Dish actualDish = helper.getByIdDish(generatedId);
                assertNull(actualDish);
                logger.info("Delete: OK");
        }
        */
}
