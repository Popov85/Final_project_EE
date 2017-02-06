package com.goit.popov.restaurant.service.dataTables.dao;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.dataTables.DataTablesDAOServerSideSearch;
import com.goit.popov.restaurant.service.dataTables.DataTablesDAOServerSideSearchUniversal;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Andrey on 2/4/2017.
 */
public class OrderByWaiterServerSideDAOProcessing extends DataTablesDAOServerSideSearchUniversal<Order> {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(OrderByWaiterServerSideDAOProcessing.class);

        @Override
        protected CriteriaQuery<Order> toFilter(DataTablesInputExtendedDTO dt, CriteriaBuilder builder,
                                                CriteriaQuery<Order> criteriaQuery, Root<Order> root, String[] params) {
                int waiterId = Integer.parseInt(params[0]);
                Path<Integer> w = root.get("waiter").<Integer>get("id");
                Predicate waiter = builder.equal(w, waiterId);
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(waiter);
                if (!dt.getColumnSearch().isEmpty()) {
                        filterDate(dt, builder, root, predicates);
                        filterTable(dt, builder, root, predicates);
                }
                criteriaQuery.where(predicates.toArray(new Predicate[]{}));
                return criteriaQuery;
        }

        private List<Predicate> filterTable(DataTablesInputExtendedDTO dt, CriteriaBuilder builder,
                                            Root<Order> orderRoot, List<Predicate> predicates) {
                if (dt.getColumnSearch().containsKey("table")) {
                        String table = dt.getColumnSearch().get("table");
                        Path<String> t = orderRoot.get("table");
                        predicates.add(builder.equal(t, table));
                }
                return predicates;
        }

        private List<Predicate> filterDate(DataTablesInputExtendedDTO dt, CriteriaBuilder builder,
                                           Root<Order> orderRoot, List<Predicate> predicates) {
                if (dt.getColumnSearch().containsKey("openedTimeStamp")) {
                        String openDate = dt.getColumnSearch().get("openedTimeStamp");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = null;
                        try {
                                date = formatter.parse(openDate);
                        } catch (ParseException e) {
                                logger.error("ERROR" + e.getMessage());
                        }
                        predicates.add(builder.greaterThanOrEqualTo(orderRoot.<Date>get("openedTimeStamp"), date));
                }
                return predicates;
        }
}
