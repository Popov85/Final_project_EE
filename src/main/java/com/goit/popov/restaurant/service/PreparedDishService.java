package com.goit.popov.restaurant.service;

import com.goit.popov.restaurant.dao.entity.PreparedDishHistoryDAO;
import com.goit.popov.restaurant.model.PreparedDish;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * Created by Andrey on 1/15/2017.
 */
public class PreparedDishService {
        @Autowired
        private PreparedDishHistoryDAO preparedDishDAO;

        public List<PreparedDish> getAll() {
                System.out.println("Service: "+preparedDishDAO);
                return preparedDishDAO.getAll();
        }


        public List<PreparedDish> getAllChefToday(int chefId) {
                return preparedDishDAO.getAllChefToday(chefId);
        }

}
