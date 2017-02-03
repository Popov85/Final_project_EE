package com.goit.popov.restaurant.dao.impl;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.DishDAO;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.service.dataTables.DataTablesDAOServerSideSearch;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
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
public class DishDAOImplJPA extends DataTablesDAOServerSideSearch<Dish> implements DishDAO {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(DishDAOImplJPA.class);

        @Autowired
        private SessionFactory sessionFactory;

        /*@PersistenceContext(unitName = "entityManagerFactory")
        private EntityManager em;*/

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

        /*@Override
        public List<Dish> getAllItems(DataTablesInputExtendedDTO dt) {
                List<Dish> resultOrders = new ArrayList<>();
                try {
                        CriteriaBuilder builder = em.getCriteriaBuilder();
                        CriteriaQuery<Dish> criteriaQuery = builder.createQuery(Dish.class);
                        Root<Dish> dishRoot = criteriaQuery.from(Dish.class);
                        criteriaQuery.select(dishRoot);
                        criteriaQuery.distinct(true);
                        criteriaQuery = toFilter(dt, builder, criteriaQuery, dishRoot);
                        criteriaQuery = toSort(dt, builder, criteriaQuery, dishRoot);
                        resultOrders = toPage(dt, criteriaQuery).getResultList();
                } catch (Exception e) {
                        logger.error("ERROR: " + e.getMessage());
                }
                return resultOrders;
        }*/

        @Override
        protected CriteriaQuery<Dish> toFilter(DataTablesInputExtendedDTO dt, CriteriaBuilder builder,
                                                   CriteriaQuery<Dish> criteriaQuery, Root<Dish> dishRoot) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!dt.getColumnSearch().isEmpty()) {
                        if (dt.getColumnSearch().containsKey("name")) {
                                String ingredient = dt.getColumnSearch().get("name");
                                predicates.add(builder.like(dishRoot.<String>get("name"), ingredient + "%"));
                        }
                        if (dt.getColumnSearch().containsKey("category")) {
                                String ingredient = dt.getColumnSearch().get("category");
                                predicates.add(builder.like(dishRoot.<String>get("category"), ingredient + "%"));
                        }
                        // TODO more selects
                }
                criteriaQuery.where(predicates.toArray(new Predicate[]{}));
                return criteriaQuery;
        }

        /*private CriteriaQuery<Dish> toSort(DataTablesInputExtendedDTO dt, CriteriaBuilder builder,
                                                 CriteriaQuery<Dish> criteriaQuery, Root<Dish> dishRoot) {
                if (dt.getDir().equals("asc")) {
                        criteriaQuery.orderBy(builder.asc(dishRoot.get(dt.getColumnName())));
                } else {
                        criteriaQuery.orderBy(builder.desc(dishRoot.get(dt.getColumnName())));
                }
                return criteriaQuery;
        }

        private TypedQuery<Dish> toPage(DataTablesInputExtendedDTO dt,
                                              CriteriaQuery<Dish> criteriaQuery) {
                final TypedQuery<Dish> typedQuery = em.createQuery(criteriaQuery);
                typedQuery
                        .setFirstResult(dt.getStart())
                        .setMaxResults(dt.getLength());
                return typedQuery;
        }*/
}
