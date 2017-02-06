package com.goit.popov.restaurant.service.dataTables.dao;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Order;
import com.goit.popov.restaurant.service.dataTables.DataTablesDAOServerSideSearch;
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
public class OrderServerSideDAOProcessing extends DataTablesDAOServerSideSearch<Order> {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(OrderServerSideDAOProcessing.class);

        @Override
        protected CriteriaQuery<Order> toFilter(DataTablesInputExtendedDTO dt, CriteriaBuilder builder,
                                                CriteriaQuery<Order> criteriaQuery, Root<Order> root) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!dt.getColumnSearch().isEmpty()) {
                        filterDate(dt, builder, root, predicates);
                        filterTable(dt, builder, root, predicates);
                        if (dt.getColumnSearch().containsKey("waiter")) {
                                String waiter = dt.getColumnSearch().get("waiter");
                                predicates.add(builder.like(root.<String>get("waiter").get("name"), waiter + "%"));
                        }
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
