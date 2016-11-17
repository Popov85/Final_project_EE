package com.goit.popov.restaurant.dao;

import com.goit.popov.restaurant.model.Employee;
import com.goit.popov.restaurant.model.Position;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;

/**
 * Created by Andrey on 28.10.2016.
 */
public class Helper {

        public static final SimpleDateFormat format =
                new SimpleDateFormat ("yyyy-MM-dd");

        @Autowired
        private SessionFactory sessionFactory;

        public void setSessionFactory(SessionFactory sessionFactory) {
                this.sessionFactory = sessionFactory;
        }

        @Transactional
        public Position getByIdPosition(int id) {
                return sessionFactory.getCurrentSession().get(Position.class, id);
        }

        @Transactional
        public Employee getByIdEmployee(int id) {
                return sessionFactory.getCurrentSession().get(Employee.class, id);
        }

        @Transactional
        public void insertPosition(Position position) {
                sessionFactory.getCurrentSession().save(position);
        }
        @Transactional
        public void deletePosition(Position position) {
                sessionFactory.getCurrentSession().remove(position);
        }
}
