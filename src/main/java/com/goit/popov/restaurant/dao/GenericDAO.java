package com.goit.popov.restaurant.dao;

import java.util.List;

/**
 * Generic interface for CRUD operations
 * @Author: Andrey P.
 * @version 1.0
 */
public interface GenericDAO<T> {
        Long insert(T t);
        void update(T t);
        List<T> getAll();
        T getById(Long id);
        void delete(T t);
}
