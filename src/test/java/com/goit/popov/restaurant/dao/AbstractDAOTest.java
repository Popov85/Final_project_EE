package com.goit.popov.restaurant.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Andrey on 11/17/2016.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-context.xml"})
public abstract class AbstractDAOTest {

        @Test
        public void test() {
                init();
                // Create
                 insert();
                // Read by id
                read();
                // Read all
                readAll();
                // Update
                update();
                // Delete
                delete();
        }

        protected abstract void init();

        protected abstract void insert();

        protected abstract void read();

        protected abstract void readAll();

        protected abstract void update();

        protected abstract void delete();
}
