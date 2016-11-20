package com.goit.popov.restaurant.dao;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.OrderDAO;
import com.goit.popov.restaurant.model.*;
import org.junit.After;
import org.junit.Before;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Andrey on 18.11.2016.
 */
public class OrderDAOTest extends AbstractDAOTest {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(OrderDAOTest.class);

        private static final boolean ORDER_OPENED_UPD = false;
        private static final int ORDER_TAB_UPD = 12;
        private static final String ORDER_OP_TIME_UPD = "2010-01-03 10:11:11";
        private static final String ORDER_CL_TIME_UPD = "2010-01-03 12:33:03";

        @Autowired
        private Helper helper;
        @Autowired
        private OrderDAO orderDAO;
        @Autowired
        public Order expectedOrder1;
        @Autowired
        public Dish expectedDish1;
        @Autowired
        public Dish expectedDish2;
        @Autowired
        private Ingredient expectedIngredient1;
        @Autowired
        private Ingredient expectedIngredient2;
        @Autowired
        private Ingredient expectedIngredient3;
        @Autowired
        private Ingredient expectedIngredient4;
        @Autowired
        private Waiter expectedWaiter1;
        @Autowired
        private Position expectedPosition;

        private Order actualOrder;

        private int generatedId;

        public void setHelper(Helper helper) {
                this.helper = helper;
        }

        public void setOrderDAO(OrderDAO orderDAO) {
                orderDAO = orderDAO;
        }

        public Order getExpectedOrder1() {
                return expectedOrder1;
        }

        public void setExpectedDish1(Dish expectedDish1) {
                this.expectedDish1 = expectedDish1;
        }

        public void setExpectedDish2(Dish expectedDish2) {
                this.expectedDish2 = expectedDish2;
        }

        public void setExpectedIngredient1(Ingredient expectedIngredient1) {
                this.expectedIngredient1 = expectedIngredient1;
        }

        public void setExpectedIngredient2(Ingredient expectedIngredient2) {
                this.expectedIngredient2 = expectedIngredient2;
        }

        public void setExpectedIngredient3(Ingredient expectedIngredient3) {
                this.expectedIngredient3 = expectedIngredient3;
        }

        public void setExpectedIngredient4(Ingredient expectedIngredient4) {
                this.expectedIngredient4 = expectedIngredient4;
        }

        public void setExpectedWaiter(Waiter expectedWaiter) {
                this.expectedWaiter1 = expectedWaiter;
        }

        public void setExpectedPosition(Position expectedPosition) {
                this.expectedPosition = expectedPosition;
        }

        @Before
        public void setUp() throws Exception {
                createDependencies();
        }

        @After
        public void tearDown() throws Exception {
                deleteDependencies();
        }

        private void createDependencies() {
                helper.insertIngredient(expectedIngredient1);
                helper.insertIngredient(expectedIngredient2);
                helper.insertIngredient(expectedIngredient3);
                helper.insertIngredient(expectedIngredient4);
                helper.insertDish(expectedDish1);
                helper.insertDish(expectedDish2);
                helper.insertPosition(expectedPosition);
                helper.insertWaiter(expectedWaiter1);
                logger.info("References inserted: OK");
        }

        private void deleteDependencies() {
                helper.deleteDish(expectedDish1);
                helper.deleteDish(expectedDish2);
                helper.deleteIngredient(expectedIngredient1);
                helper.deleteIngredient(expectedIngredient2);
                helper.deleteIngredient(expectedIngredient3);
                helper.deleteIngredient(expectedIngredient4);
                helper.deleteWaiter(expectedWaiter1);
                helper.deletePosition(expectedPosition);
                logger.info("References deleted: OK");
        }

        @Override
        protected void insert() {
                generatedId = orderDAO.insert(expectedOrder1);
                assertNotNull(generatedId);
                actualOrder = helper.getByIdOrder(generatedId);
                assertEquals(expectedOrder1, actualOrder);
                logger.info("Insert: OK");
        }

        @Override
        protected void read() {
                Order expectedOrder = orderDAO.getById(generatedId);
                assertEquals(actualOrder, expectedOrder);
                logger.info("Read: OK");
        }

        @Override
        protected void readAll() {
                List<Order> orderList = orderDAO.getAll();
                assertNotNull(orderList.size());
                logger.info("Read all: OK");
        }

        @Override
        protected void update() {
                expectedOrder1.setOpened(ORDER_OPENED_UPD);
                expectedOrder1.setTable(ORDER_TAB_UPD);
                try {
                        expectedOrder1.setOpenedTimeStamp(Helper.format.parse(ORDER_OP_TIME_UPD));
                        expectedOrder1.setClosedTimeStamp(Helper.format.parse(ORDER_CL_TIME_UPD));
                } catch (ParseException e) {
                        e.printStackTrace();
                }
                orderDAO.update(expectedOrder1);
                Order updatedOrder = helper.getByIdOrder(generatedId);
                assertEquals(expectedOrder1, updatedOrder);
                logger.info("Update: OK");
        }

        @Override
        protected void delete() {
                orderDAO.delete(actualOrder);
                Order actualOrder = helper.getByIdOrder(generatedId);
                assertNull(actualOrder);
                logger.info("Delete: OK");
        }
}
