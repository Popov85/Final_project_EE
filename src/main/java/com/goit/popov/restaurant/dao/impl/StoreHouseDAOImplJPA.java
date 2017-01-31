package com.goit.popov.restaurant.dao.impl;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.StoreHouseDAO;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.model.StoreHouse;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;
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
public class StoreHouseDAOImplJPA implements StoreHouseDAO {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(StoreHouseDAOImplJPA.class);

        private SessionFactory sessionFactory;

        public void setSessionFactory(SessionFactory sessionFactory) {
                this.sessionFactory = sessionFactory;
        }

        @PersistenceContext(unitName = "entityManagerFactory")
        private EntityManager em;

        @Override
        public int insert(StoreHouse sh) {
                return (int) sessionFactory.getCurrentSession().save(sh);
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
        public StoreHouse getById(int id) {
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
        public long count() {
                return (long) sessionFactory.getCurrentSession().createQuery("select count(*) from StoreHouse").uniqueResult();
        }

        @Override
        public List<StoreHouse> getAllIngredients(DataTablesInputExtendedDTO dt) {
                List<StoreHouse> resultOrders = new ArrayList<>();
                try {
                        CriteriaBuilder builder = em.getCriteriaBuilder();
                        CriteriaQuery<StoreHouse> criteriaQuery = builder.createQuery(StoreHouse.class);
                        Root<StoreHouse> orderRoot = criteriaQuery.from(StoreHouse.class);
                        criteriaQuery.select(orderRoot);
                        criteriaQuery.distinct(true);
                        criteriaQuery = toFilter(dt, builder, criteriaQuery, orderRoot);
                        criteriaQuery = toSort(dt, builder, criteriaQuery, orderRoot);
                        resultOrders = toPage(dt, criteriaQuery).getResultList();
                } catch (Exception e) {
                        logger.error("ERROR: " + e.getMessage());
                }
                return resultOrders;
        }

        private CriteriaQuery<StoreHouse> toFilter(DataTablesInputExtendedDTO dt, CriteriaBuilder builder,
                                              CriteriaQuery<StoreHouse> criteriaQuery, Root<StoreHouse> orderRoot) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!dt.getColumnSearch().isEmpty()) {
                        if (dt.getColumnSearch().containsKey("ingredient")) {
                                String ingredient = dt.getColumnSearch().get("ingredient");
                                predicates.add(builder.like(orderRoot.<String>get("ingredient").get("name"), ingredient + "%"));
                        }
                }
                criteriaQuery.where(predicates.toArray(new Predicate[]{}));
                return criteriaQuery;
        }

        private CriteriaQuery<StoreHouse> toSort(DataTablesInputExtendedDTO dt, CriteriaBuilder builder,
                                            CriteriaQuery<StoreHouse> criteriaQuery, Root<StoreHouse> orderRoot) {
                if (dt.getDir().equals("asc")) {
                        criteriaQuery.orderBy(builder.asc(orderRoot.get(dt.getColumnName())));
                } else {
                        criteriaQuery.orderBy(builder.desc(orderRoot.get(dt.getColumnName())));
                }
                return criteriaQuery;
        }

        private TypedQuery<StoreHouse> toPage(DataTablesInputExtendedDTO dt,
                                         CriteriaQuery<StoreHouse> criteriaQuery) {
                final TypedQuery<StoreHouse> typedQuery = em.createQuery(criteriaQuery);
                typedQuery
                        .setFirstResult(dt.getStart())
                        .setMaxResults(dt.getLength());
                return typedQuery;
        }

}
