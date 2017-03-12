package com.goit.popov.restaurant.dao.impl;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.RoleDAO;
import com.goit.popov.restaurant.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Andrey on 3/12/2017.
 */
@Transactional
public class RoleDAOImplJPA implements RoleDAO {

    private static final Logger lOG = (Logger) LoggerFactory.getLogger(RoleDAOImplJPA.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long insert(Role role) {
        Session session = sessionFactory.getCurrentSession();
        Long id = (Long) session.save(role);
        //session.flush();
        return id;
    }

    @Override
    public void update(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.update(role);
        //session.flush();
    }

    @Override
    public List<Role> getAll() {
        return sessionFactory.getCurrentSession().createQuery("select r from Role r").list();
    }

    @Override
    public Role getById(Long id) {
        return sessionFactory.getCurrentSession().get(Role.class, id);
    }

    @Override
    public void delete(Role role) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(role);
        //session.flush();
    }
}
