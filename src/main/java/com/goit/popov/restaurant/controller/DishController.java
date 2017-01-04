package com.goit.popov.restaurant.controller;

import com.goit.popov.restaurant.service.DishService;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOCollectionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Andrey on 1/4/2017.
 */
@Controller
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping("/get_all_dishes")
    @ResponseBody
    public DataTablesOutputDTOCollectionWrapper getDishes() {
        DataTablesOutputDTOCollectionWrapper data = new DataTablesOutputDTOCollectionWrapper();
        data.setData(dishService.toJSON(dishService.getAll()));
        return data;
    }

    @GetMapping("/show_ingredients")
    @ResponseBody
    public ModelAndView getDishes(@RequestParam int id, ModelAndView modelAndView) {
        modelAndView.addObject("id",dishService.getById(id).getId());
        modelAndView.addObject("dish",dishService.getById(id).getName());
        modelAndView.setViewName("th/dishs_ingredients");
        return modelAndView;
    }

    @PostMapping("/get_dishs_ingredients")
    @ResponseBody
    public DataTablesOutputDTOCollectionWrapper getDishIngredients(@RequestParam int id) {
        DataTablesOutputDTOCollectionWrapper data = new DataTablesOutputDTOCollectionWrapper();
        data.setData(dishService.toJSON(dishService.getById(id).getIngredients()));
        return data;
    }

}
