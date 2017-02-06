package com.goit.popov.restaurant.dao.impl;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.DishDAO;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.service.dataTables.DataTablesDAOServerSideSearch;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import com.goit.popov.restaurant.service.dataTables.dao.DishServerSideDAOProcessing;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 28.10.2016.
 */
@Transactional
public class DishDAOImplJPA implements DishDAO {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(DishDAOImplJPA.class);

        @Autowired
        private SessionFactory sessionFactory;

        @Autowired
        private DishServerSideDAOProcessing dishServerSideDAOProcessing;


        @Override
        public int insert(Dish dish) {
                return (int) sessionFactory.getCurrentSession().save(dish);
        }

        @Override
        public void update(Dish dish) {
                sessionFactory.getCurrentSession().update(dish);
        }

        @Override
        public List<Dish> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select d from Dish d").list();
        }

        @Override
        public Dish getById(int id) {
                return sessionFactory.getCurrentSession().get(Dish.class, id);
        }

        @Override
        public void delete(Dish dish) {
                sessionFactory.getCurrentSession().delete(dish);
        }

        @Override
        public void deleteById(int id) {
                delete(getById(id));
        }

        @Override
        public long count() {
                return (long) sessionFactory.getCurrentSession().createQuery("select count(*) from Dish").uniqueResult();
        }

        @Override
        public List<Dish> getAllItems(DataTablesInputExtendedDTO dt) {
                return dishServerSideDAOProcessing.getAllItems(dt);
        }
}
