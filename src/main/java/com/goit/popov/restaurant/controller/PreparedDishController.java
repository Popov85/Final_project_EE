package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.PreparedDish;
import com.goit.popov.restaurant.service.PreparedDishService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Andrey on 1/15/2017.
 */
@Controller
public class PreparedDishController {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(PreparedDishController.class);

        @Autowired
        private PreparedDishService preparedDishService;

        @GetMapping("/get_prepared_dishes")
        @ResponseBody
        public List<PreparedDish> getPreparedDishes() {
                logger.info("List: "+preparedDishService.getAll());
                return preparedDishService.getAll();
        }

        @GetMapping("/today/get_prepared_dishes")
        @ResponseBody
        public List<PreparedDish> getAllChefToday(@RequestParam int chefId) {
                logger.info("List: "+preparedDishService.getAllChefToday(chefId));
                return preparedDishService.getAllChefToday(chefId);
        }

}
