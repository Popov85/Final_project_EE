package com.goit.popov.restaurant.dao;

import com.goit.popov.restaurant.dao.entity.PositionDAO;
import com.goit.popov.restaurant.model.Position;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Andrey on 27.10.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-context.xml"})
public class PositionDAOTestNew {

        private static final String POSITION_UPD = "Janitor";

        @Autowired
        private Position expectedPosition;

        @Autowired
        private PositionDAO positionDAO;

        @Autowired
        private Helper helper;

        public void setExpectedPosition(Position expectedPosition) {
                this.expectedPosition = expectedPosition;
        }

        public void setPositionDAO(PositionDAO positionDAO) {
                this.positionDAO = positionDAO;
        }

        public void setHelper(Helper helper) {
                this.helper = helper;
        }

        private Position actualPosition;

        private int generatedId;

        @Test
        public void test() {
                System.out.println("expectedPosition = "+expectedPosition);
                System.out.println("positionDAO = "+positionDAO);
                System.out.println("helper = "+helper);
                insert();
                read();
                readAll();
                update();
                delete();
        }

        private void insert() {
                generatedId = positionDAO.insert(expectedPosition);
                assertNotNull(generatedId);
                actualPosition = helper.getByIdPosition(generatedId);
                assertEquals(expectedPosition, actualPosition);
        }

        private void read() {
                expectedPosition = positionDAO.getById(generatedId);
                assertEquals(actualPosition, expectedPosition);
        }

        private void update() {
                expectedPosition.setName(POSITION_UPD);
                positionDAO.update(expectedPosition);
                Position updatedPosition = helper.getByIdPosition(generatedId);
                assertEquals(expectedPosition, updatedPosition);
        }

        private void readAll() {
                List<Position> positionList = positionDAO.getAll();
                assertNotNull(positionList.size());
        }

        private void delete() {
                positionDAO.delete(this.actualPosition);
                Position actualPosition = helper.getByIdPosition(generatedId);
                assertNull(actualPosition);
        }
}
