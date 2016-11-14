package com.goit.popov.restaurant.dao.impl;

import com.goit.popov.restaurant.dao.entity.EmployeeDAO;
import com.goit.popov.restaurant.model.Employee;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrey on 26.10.2016.
 */
public class EmployeeDAOImplJPA implements EmployeeDAO {

        private SessionFactory sessionFactory;

        public void setSessionFactory(SessionFactory sessionFactory) {
                this.sessionFactory = sessionFactory;
        }

        @Transactional
        @Override
        public int insert(Employee employee) {
                return (int) sessionFactory.getCurrentSession().save(employee);
        }

        @Transactional
        @Override
        public void update(Employee employee) {
                sessionFactory.getCurrentSession().update(employee);
        }

        @Transactional
        @Override
        public List<Employee> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select e from Employee e order by e.id").list();
        }

        @Transactional
        @Override
        public Employee getByName(String name) {
                Query query = sessionFactory.getCurrentSession().createQuery("select e from Employee e " +
                        "where e.name like :name");
                query.setParameter("name", name);
                return (Employee) query.uniqueResult();
        }

        @Transactional
        @Override
        public void delete(Employee employee) {
                sessionFactory.getCurrentSession().delete(employee);
        }

        @Transactional(propagation = Propagation.REQUIRED)
        @Override
        public Employee getById(int id) {
                return sessionFactory.getCurrentSession().get(Employee.class, id);
        }
}
