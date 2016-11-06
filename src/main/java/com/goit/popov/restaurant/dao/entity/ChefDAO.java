package com.goit.popov.restaurant.dao.entity;

import com.goit.popov.restaurant.model.Chef;

/**
 * Created by Andrey on 11/5/2016.
 */
public interface ChefDAO extends GenericDAO<Chef> {
        Chef getByName(String name);
}
