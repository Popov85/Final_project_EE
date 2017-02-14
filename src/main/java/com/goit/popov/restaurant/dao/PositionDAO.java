package com.goit.popov.restaurant.dao;

import com.goit.popov.restaurant.model.Position;

/**
 * Created by Andrey on 20.10.2016.
 */
public interface PositionDAO extends GenericDAO<Position> {

        Position getByName(String name);
}
