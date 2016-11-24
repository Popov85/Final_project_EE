package com.goit.popov.restaurant.dao;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.PositionDAO;
import com.goit.popov.restaurant.model.Position;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Andrey on 27.10.2016.
 */
@Transactional
public class PositionDAOTest extends AbstractDAOTest {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(PositionDAOTest.class);

        private static final String POSITION_UPD = "Janitor";

        @Autowired
        private Position expectedPosition;

        @Autowired
        private PositionDAO positionDAO;

        @Autowired
        private Helper helper;

        private Position actualPosition;

        private int generatedId;

        @Override
        public void insert() {
                generatedId = positionDAO.insert(expectedPosition);
                assertNotNull(generatedId);
                actualPosition = helper.getByIdPosition(generatedId);
                assertEquals(expectedPosition, actualPosition);
                logger.info("Insert: OK");
        }
        @Override
        public void read() {
                expectedPosition = positionDAO.getById(generatedId);
                assertEquals(actualPosition, expectedPosition);
                logger.info("Read: OK");
        }
        @Override
        public void update() {
                expectedPosition.setName(POSITION_UPD);
                positionDAO.update(expectedPosition);
                Position updatedPosition = helper.getByIdPosition(generatedId);
                assertEquals(expectedPosition, updatedPosition);
                logger.info("Update: OK");
        }
        @Override
        public void readAll() {
                List<Position> positionList = positionDAO.getAll();
                assertNotNull(positionList.size());
                logger.info("ReadAll: OK");
        }
        @Override
        public void delete() {
                positionDAO.delete(this.actualPosition);
                Position actualPosition = helper.getByIdPosition(generatedId);
                assertNull(actualPosition);
                logger.info("Delete: OK");
        }
}
