package com.goit.popov.restaurant.service.dataTables.dao;

import com.goit.popov.restaurant.model.StoreHouse;
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
public class StockServerSideDAOProcessing extends DataTablesDAOServerSideSearch<StoreHouse> {

        @Override
        protected CriteriaQuery<StoreHouse> toFilter(DataTablesInputExtendedDTO dt, CriteriaBuilder builder,
                                                     CriteriaQuery<StoreHouse> criteriaQuery, Root<StoreHouse> root) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (!dt.getColumnSearch().isEmpty()) {
                        if (dt.getColumnSearch().containsKey("ingredient")) {
                                String ingredient = dt.getColumnSearch().get("ingredient");
                                predicates.add(builder.like(root.<String>get("ingredient").get("name"), ingredient + "%"));
                        }
                }
                criteriaQuery.where(predicates.toArray(new Predicate[]{}));
                return criteriaQuery;
        }
}
