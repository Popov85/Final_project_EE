package com.goit.popov.restaurant.dao.impl;

import com.goit.popov.restaurant.dao.EmployeeDAO;
import com.goit.popov.restaurant.model.Employee;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by Andrey on 26.10.2016.
 */
@Transactional
public class EmployeeDAOImplJPA implements EmployeeDAO {

        @Autowired
        private SessionFactory sessionFactory;

        @Override
        public Long insert(Employee employee) {
                return (Long) sessionFactory.getCurrentSession().save(employee);
        }

        @Override
        public void update(Employee employee) {
                sessionFactory.getCurrentSession().update(employee);
        }

        @Override
        public List<Employee> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select e from Employee e order by e.id").list();
        }

        @Override
        public Employee getByLogin(String login) {
                Query query = sessionFactory.getCurrentSession().createQuery("select e from Employee e " +
                        "where e.login like :login");
                query.setParameter("login", login);
                return (Employee) query.uniqueResult();
        }

        @Override
        public void delete(Employee employee) {
                sessionFactory.getCurrentSession().delete(employee);
        }

        @Override
        public Employee getById(Long id) {
                return sessionFactory.getCurrentSession().get(Employee.class, id);
        }
}
