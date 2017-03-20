package test;

import com.goit.popov.restaurant.dao.GenericDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Andrey on 20.03.2017.
 */
public class StudentBookDAO implements GenericDAO<StudentBook> {

        @Autowired
        private SessionFactory sessionFactory;

        @Override
        public Long insert(StudentBook studentBook) {
                return (Long) sessionFactory.getCurrentSession().save(studentBook);
        }

        @Override
        public void update(StudentBook studentBook) {
                throw new UnsupportedOperationException();
        }

        @Override
        public List<StudentBook> getAll() {
                throw new UnsupportedOperationException();
        }

        @Override
        public StudentBook getById(Long id) {
                return sessionFactory.getCurrentSession().get(StudentBook.class, id);
        }

        @Override
        public void delete(StudentBook studentBook) {
                sessionFactory.getCurrentSession().delete(studentBook);
        }
}
