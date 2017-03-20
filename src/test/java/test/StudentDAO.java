package test;

import com.goit.popov.restaurant.dao.GenericDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Andrey on 20.03.2017.
 */
public class StudentDAO implements GenericDAO<Student> {

        @Autowired
        private SessionFactory sessionFactory;

        @Override
        public Long insert(Student student) {
                return (Long) sessionFactory.getCurrentSession().save(student);
        }

        @Override
        public void update(Student student) {
                throw new UnsupportedOperationException();
        }

        @Override
        public List<Student> getAll() {
                throw new UnsupportedOperationException();
        }

        @Override
        public Student getById(Long id) {
                return sessionFactory.getCurrentSession().get(Student.class, id);
        }

        @Override
        public void delete(Student student) {
                sessionFactory.getCurrentSession().delete(student);
        }
}
