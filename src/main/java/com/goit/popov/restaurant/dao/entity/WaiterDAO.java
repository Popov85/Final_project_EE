package com.goit.popov.restaurant.dao.entity;

import com.goit.popov.restaurant.model.Waiter;

/**
 * Created by Andrey on 11/5/2016.
 */
public interface WaiterDAO extends GenericDAO<Waiter> {
        Waiter getByName(String name);
}
