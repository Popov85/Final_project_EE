package com.goit.popov.restaurant.service.dataTables.dao;

import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.service.dataTables.DataTablesDAOServerSideSearch;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 2/4/2017.
 */
public class DishServerSideDAOProcessing extends DataTablesDAOServerSideSearch<Dish> {

        @Override
        protected CriteriaQuery toFilter(DataTablesInputExtendedDTO dt, CriteriaBuilder builder,
                                         CriteriaQuery criteriaQuery, Root root) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!dt.getColumnSearch().isEmpty()) {
                        if (dt.getColumnSearch().containsKey("name")) {
                                String ingredient = dt.getColumnSearch().get("name");
                                predicates.add(builder.like(root.<String>get("name"), ingredient + "%"));
                        }
                        if (dt.getColumnSearch().containsKey("category")) {
                                String ingredient = dt.getColumnSearch().get("category");
                                predicates.add(builder.like(root.<String>get("category"), ingredient + "%"));
                        }
                        // TODO more selects
                }
                criteriaQuery.where(predicates.toArray(new Predicate[]{}));
                return criteriaQuery;
        }
}
