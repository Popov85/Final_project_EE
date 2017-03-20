package test;

import ch.qos.logback.classic.Logger;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Andrey on 20.03.2017.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-context.xml", "/test-data.xml"})
public class StudentBookDAOTest {

        private static final Logger LOG = (Logger) LoggerFactory.getLogger(StudentBookDAOTest.class);

        @Autowired
        private StudentDAO studentDAO;

        @Autowired
        private SessionFactory sessionFactory;

        @Test
        public void itShouldPerformCRUDSmoothly() {
                StudentBook sb = new StudentBook();
                sb.setNumber("12345");
                Student st = new Student();
                st.setName("Mr. Jonson");
                st.setStudentBook(sb);

                Long generatedId = studentDAO.insert(st);
                sessionFactory.getCurrentSession().flush();
                assertNotNull(generatedId);
                LOG.info("genId = "+generatedId);

                studentDAO.delete(st);
                sessionFactory.getCurrentSession().flush();
                Student emptyStudent = studentDAO.getById(generatedId);
                assertNull(emptyStudent);
        }
}
