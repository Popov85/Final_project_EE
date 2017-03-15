package com.goit.popov.restaurant.dao.impl;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.StoreHouseDAO;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.StoreHouse;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import com.goit.popov.restaurant.service.dataTables.dao.StockServerSideDAOProcessing;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by Andrey on 28.10.2016.
 */
@Transactional
public class StoreHouseDAOImplJPA implements StoreHouseDAO {

        private static final Logger LOG = (Logger) LoggerFactory.getLogger(StoreHouseDAOImplJPA.class);

        @Autowired
        private SessionFactory sessionFactory;

        @Autowired
        private StockServerSideDAOProcessing stockServerSideDAOProcessing;

        @Override
        public Long insert(StoreHouse sh) {
                Object r = sessionFactory.getCurrentSession().save(sh);
                // Warning: returns exactly the same object
                LOG.info("r = "+r);
                return 1L;
        }

        @Override
        public void update(StoreHouse sh) {
                sessionFactory.getCurrentSession().update(sh);
        }

        @Override
        public List<StoreHouse> getAll() {
                return sessionFactory.getCurrentSession().createQuery("select e from StoreHouse e").list();
        }

        @Override
        public StoreHouse getById(Long id) {
                return sessionFactory.getCurrentSession().get(StoreHouse.class, id);
        }

        @Override
        public void delete(StoreHouse sh) {
                sessionFactory.getCurrentSession().delete(sh);
        }

        @Override
        public List<StoreHouse> getAllRunOut(double threshold) {
                Query query = sessionFactory.getCurrentSession().createQuery("select s from StoreHouse s " +
                        "where s.quantity < :threshold");
                query.setParameter("threshold", threshold);
                return query.list();
        }

        @Override
        public StoreHouse getByIngredient(Ingredient ingredient) {
                Query query = sessionFactory.getCurrentSession().createQuery("select s from StoreHouse s " +
                        "where s.ingredient = :ingredient");
                query.setParameter("ingredient", ingredient);
                return (StoreHouse) query.uniqueResult();
        }


        @Override
        public Double getQuantityByIngredient(Ingredient ingredient) {
                Query query = sessionFactory.getCurrentSession().createQuery("select s.quantity from StoreHouse s " +
                        "where s.ingredient = :ingredient");
                query.setParameter("ingredient", ingredient);
                return (Double) query.uniqueResult();
        }

        @Override
        public Long count() {
                return (Long) sessionFactory.getCurrentSession().createQuery("select count(*) from StoreHouse").uniqueResult();
        }

        @Override
        public List<StoreHouse> getAllItems(DataTablesInputExtendedDTO dt) {
                return stockServerSideDAOProcessing.getAllItems(dt);
        }
}
