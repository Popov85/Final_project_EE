package com.goit.popov.restaurant.dao;

import com.goit.popov.restaurant.dao.entity.DishDAO;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Ingredient;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Andrey on 18.11.2016.
 */
public class DishDAOTest extends AbstractDAOTest {

        private static final String DISH_NAME_UPD = "UpdatedName";
        private static final String DISH_CAT_UPD = "UpdatedCategory";
        private static final double DISH_WEIGHT_UPD = 0.245d;
        private static final BigDecimal DISH_PRICE_UPD = new BigDecimal(2.3);

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

        private Dish actualDish;

        private int generatedId;

        public void setHelper(Helper helper) {
                this.helper = helper;
        }

        public void setIngredientDAO(DishDAO ingredientDAO) {
                this.dishDAO = ingredientDAO;
        }

        public void setExpectedDish1(Dish expectedDish1) {
                this.expectedDish1 = expectedDish1;
        }

        public void setExpectedIngredient1(Ingredient expectedIngredient1) {
                this.expectedIngredient1 = expectedIngredient1;
        }

        public void setExpectedIngredient2(Ingredient expectedIngredient2) {
                this.expectedIngredient2 = expectedIngredient2;
        }

        @Before
        public void setUp() throws Exception {
                //displayInjections();
                createDependencies();
        }

        private void displayInjections() {
                System.out.println("helper = "+helper);
                System.out.println("dishDAO = "+dishDAO);
                System.out.println("expectedDish1 = "+expectedDish1);
                System.out.println("expectedDish1.ingredients = "+expectedDish1.getIngredients());
                System.out.println("expectedIngredient1 = "+expectedIngredient1);
                System.out.println("expectedIngredient2 = "+expectedIngredient2);
        }

        private void createDependencies() {
                helper.insertIngredient(expectedIngredient1);
                helper.insertIngredient(expectedIngredient2);
        }

        @After
        public void tearDown() throws Exception {
                deleteDependencies();
        }

        private void deleteDependencies() {
                helper.deleteIngredient(expectedIngredient1);
                helper.deleteIngredient(expectedIngredient2);
        }

        @Override
        protected void insert() {
                generatedId = dishDAO.insert(expectedDish1);
                assertNotNull(generatedId);
                actualDish = helper.getByIdDish(generatedId);
                System.out.println("actualDish: "+actualDish+" /ingredients are: "+actualDish.getIngredients());
                assertEquals(expectedDish1, actualDish);
        }

        @Override
        protected void read() {
                Dish expectedDish = dishDAO.getById(generatedId);
                assertEquals(actualDish, expectedDish);
        }

        @Override
        protected void readAll() {
                List<Dish> dishList = dishDAO.getAll();
                assertNotNull(dishList.size());
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
        }

        @Override
        protected void delete() {
                dishDAO.delete(actualDish);
                Dish actualDish = helper.getByIdDish(generatedId);
                assertNull(actualDish);
        }
}
