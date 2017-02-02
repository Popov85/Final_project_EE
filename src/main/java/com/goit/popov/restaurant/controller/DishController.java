package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.service.DishService;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOCollectionWrapper;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOUniversal;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Andrey on 1/4/2017.
 */
@Controller
public class DishController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(DishController.class);

    @Autowired
    private DishService dishService;

    @GetMapping(value = "/admin/dishes")
    public String showDishes() {
        return "th/manager/dishes";
    }

    @GetMapping(value = "/admin/new_dish")
    public ModelAndView showDishForm(){
        Dish dish = new Dish();
        //position.setName("");
        return new ModelAndView("th/manager/new_dish", "dish", dish);
    }

    @PostMapping(value = "/get_all_dishes")
    @ResponseBody
    public DataTablesOutputDTOUniversal<Dish> getDishes(DataTablesInputExtendedDTO input) {
        DataTablesOutputDTOUniversal<Dish> data = dishService.getAll(input);
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

    @PostMapping(value="/admin/save_dish")
    public String saveDish(@Valid @ModelAttribute("dish") Dish dish, BindingResult result){
        if (result.hasErrors()) {
            logger.info("Errors number while saving new dish: "+
                    result.getFieldErrorCount());
            logger.info("Weight error value is: "+result.getFieldError("weight").getRejectedValue());
            return "th/manager/new_dish";
        }
        dishService.insert(dish);
        return "redirect:/admin/dishes";
    }

    @GetMapping(value = "/admin/edit_dish")
    public String showDishEditForm(@RequestParam("id") int id, ModelMap map){
        Dish dish = dishService.getById(id);
        map.addAttribute("dish", dish);
        return "th/manager/edit_dish";
    }

    @PostMapping(value="/admin/update_dish")
    public String updateDish(@Valid @ModelAttribute("dish") Dish dish, BindingResult result){
        if (result.hasErrors()) {
            logger.info("Errors number while updating dish: "+
                    result.getFieldErrorCount());
            return "th/manager/edit_dish";
        } else {
            dishService.update(dish);
            return "redirect:/admin/dishes";
        }
    }

    @GetMapping(value = "/get_all_dishes2")
    @ResponseBody
    public List<Dish> getDishes() {
        logger.info("Dishes: "+dishService.getAll());
        return dishService.getAll();
    }
}
