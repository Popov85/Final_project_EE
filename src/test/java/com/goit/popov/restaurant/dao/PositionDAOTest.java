package com.goit.popov.restaurant.dao;

import com.goit.popov.restaurant.dao.entity.PositionDAO;
import com.goit.popov.restaurant.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Andrey on 27.10.2016.
 */
public class PositionDAOTest extends AbstractDAOTest {

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

        public void insert() {
                generatedId = positionDAO.insert(expectedPosition);
                assertNotNull(generatedId);
                actualPosition = helper.getByIdPosition(generatedId);
                assertEquals(expectedPosition, actualPosition);
        }

        public void read() {
                expectedPosition = positionDAO.getById(generatedId);
                assertEquals(actualPosition, expectedPosition);
        }

        public void update() {
                expectedPosition.setName(POSITION_UPD);
                positionDAO.update(expectedPosition);
                Position updatedPosition = helper.getByIdPosition(generatedId);
                assertEquals(expectedPosition, updatedPosition);
        }

        public void readAll() {
                List<Position> positionList = positionDAO.getAll();
                assertNotNull(positionList.size());
        }

        public void delete() {
                positionDAO.delete(this.actualPosition);
                Position actualPosition = helper.getByIdPosition(generatedId);
                assertNull(actualPosition);
        }
}
