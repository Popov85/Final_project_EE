package com.goit.popov.restaurant.dao;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.WaiterDAO;
import com.goit.popov.restaurant.model.*;
import org.junit.After;
import org.junit.Before;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Andrey on 18.11.2016.
 */
@Transactional
public class WaiterDAOTest extends AbstractDAOTest {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(WaiterDAOTest.class);

        private static final String WAIT_NAME_UPD = "WaiterTest";
        private static final String WAIT_DOB_UPD = "1988-07-08";
        private static final String WAIT_UPD_PHONE = "+380632030026";
        private static final BigDecimal WAIT_UPD_SAL = new BigDecimal(10000);

        @Autowired
        private Helper helper;
        @Autowired
        private WaiterDAO waiterDAO;
        @Autowired
        private Waiter expectedWaiter1;
        @Autowired
        private Position expectedPosition;
        @Autowired
        private Order expectedOrder3;
        @Autowired
        private Dish expectedDish1;
        @Autowired
        private Ingredient expectedIngredient1;
        @Autowired
        private Ingredient expectedIngredient2;

        private Waiter actualWaiter;

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

        private void createDependencies() {
                helper.insertPosition(expectedPosition);
                generatedId = waiterDAO.insert(expectedWaiter1);
                helper.insertIngredient(expectedIngredient1);
                helper.insertIngredient(expectedIngredient2);
                helper.insertDish(expectedDish1);
                helper.insertOrder(expectedOrder3);
        }

        private void deleteDependencies() {
                helper.deleteDish(expectedDish1);
                helper.deleteIngredient(expectedIngredient1);
                helper.deleteIngredient(expectedIngredient2);
        }

        @Override
        protected void insert() {
                actualWaiter = helper.getByIdWaiter(generatedId);
                assertEquals(expectedWaiter1, actualWaiter);
                logger.info("Insert: OK");
        }

        @Override
        protected void read() {
                expectedWaiter1 = waiterDAO.getById(generatedId);
                assertEquals(actualWaiter, expectedWaiter1);
                logger.info("Read: OK");
        }

        @Override
        protected void readAll() {
                List<Waiter> waiterList = waiterDAO.getAll();
                assertNotNull(waiterList.size());
                logger.info("Read all: OK");
        }

        @Override
        protected void update() {
                expectedWaiter1.setName(WAIT_NAME_UPD);
                try {
                        expectedWaiter1.setDob(Helper.format.parse(WAIT_DOB_UPD));
                } catch (ParseException e) {
                        e.printStackTrace();
                }
                expectedWaiter1.setPhone(WAIT_UPD_PHONE);
                expectedWaiter1.setSalary(WAIT_UPD_SAL);
                List<Order> orders = new ArrayList<>();
                orders.add(expectedOrder3);
                expectedWaiter1.setOrders(orders);
                waiterDAO.update(expectedWaiter1);
                Waiter updatedWaiter = helper.getByIdWaiter(generatedId);
                assertEquals(expectedWaiter1, updatedWaiter);
                logger.info("Update: OK");
        }

        @Override
        protected void delete() {
                helper.deleteOrder(expectedOrder3);
                Waiter waiter = helper.getByIdWaiter(generatedId);
                waiterDAO.delete(waiter);
                helper.deletePosition(expectedPosition);
                Waiter actualWaiter = helper.getByIdWaiter(generatedId);
                assertNull(actualWaiter);
                logger.info("Delete: OK");
        }
}
