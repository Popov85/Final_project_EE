package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.entity.DishDAO;
import com.goit.popov.restaurant.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Andrey on 02.12.2016.
 */
public class DishService {

        @Autowired
        private DishDAO dishDAO;

        public void insert(Dish dish) {
                dishDAO.insert(dish);
        }

        public void update(Dish dish) {
                dishDAO.update(dish);
        }

        public List<Dish> getAll() {
                return dishDAO.getAll();
        }

        public Dish getById(int id) {
                return dishDAO.getById(id);
        }

        public void delete(Dish dish) {
                dishDAO.delete(dish);
        }
}
