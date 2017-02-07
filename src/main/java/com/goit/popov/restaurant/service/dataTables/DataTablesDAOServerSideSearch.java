package com.goit.popov.restaurant.service.dataTables;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * @See http://stackoverflow.com/questions/3437897/how-to-get-a-class-instance-of-generics-type-t
 * Created by Andrey on 02.02.2017.
 */
public abstract class DataTablesDAOServerSideSearch<T>{

        private static final Logger logger = (Logger) LoggerFactory.getLogger(DataTablesDAOServerSideSearch.class);

        @PersistenceContext(unitName = "entityManagerFactory")
        private EntityManager em;

        private final Class genericType = ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);

        public List<T> getAllItems(DataTablesInputExtendedDTO dt) {
                logger.info("Actual Type: "+genericType);
                List<T> result = new ArrayList<>();
                try {
                        CriteriaBuilder builder = em.getCriteriaBuilder();
                        CriteriaQuery<T> criteriaQuery = builder.createQuery(genericType);
                        Root<T> root = criteriaQuery.from(genericType);
                        criteriaQuery.select(root);
                        criteriaQuery.distinct(true);
                        criteriaQuery = toFilter(dt, builder, criteriaQuery, root);
                        criteriaQuery = toSort(dt, builder, criteriaQuery, root);
                        result = toPage(dt, criteriaQuery).getResultList();
                } catch (Exception e) {
                        logger.error("ERROR: " + e.getMessage()+" class: "+e.getClass());
                }
                return result;
        }

        protected abstract CriteriaQuery<T> toFilter(DataTablesInputExtendedDTO dt, CriteriaBuilder builder,
                                                     CriteriaQuery<T> criteriaQuery, Root<T> root);

        protected CriteriaQuery<T> toSort(DataTablesInputExtendedDTO dt, CriteriaBuilder builder,
                                                      CriteriaQuery<T> criteriaQuery, Root<T> root) {
                if (dt.getDir().equals("asc")) {
                        criteriaQuery.orderBy(builder.asc(root.get(dt.getColumnName())));
                } else {
                        criteriaQuery.orderBy(builder.desc(root.get(dt.getColumnName())));
                }
                return criteriaQuery;
        }

        protected TypedQuery<T> toPage(DataTablesInputExtendedDTO dt, CriteriaQuery<T> criteriaQuery) {
                final TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);
                typedQuery
                        .setFirstResult(dt.getStart())
                        .setMaxResults(dt.getLength());
                return typedQuery;
        }
}
