package com.goit.popov.restaurant.dao.entity;

import com.goit.popov.restaurant.model.PreparedDish;
import java.util.List;

/**
 * Created by Andrey on 10/14/2016.
 */
public interface PreparedDishHistoryDAO {
        int addPreparedDish(PreparedDish dish);
        List<PreparedDish> getAll();
}
