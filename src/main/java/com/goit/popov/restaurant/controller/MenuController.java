package com.goit.popov.restaurant.controller;

import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.service.MenuService;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOCollectionWrapper;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Andrey on 1/2/2017.
 */
@Controller
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping("/get_menus")
    @ResponseBody
    public DataTablesOutputDTOCollectionWrapper getMenus() {
        DataTablesOutputDTOCollectionWrapper data = new DataTablesOutputDTOCollectionWrapper();
        data.setData(menuService.toJSON(menuService.getAll()));
        return data;
    }

    @GetMapping("/show_dishes")
    @ResponseBody
    public ModelAndView getDishes(@RequestParam int id, ModelAndView modelAndView) {
        modelAndView.addObject("id",menuService.getById(id).getId());
        modelAndView.addObject("menu",menuService.getById(id).getName());
        modelAndView.setViewName("th/menus_dishes");
        return modelAndView;
    }

    @PostMapping("/get_menus_dishes")
    @ResponseBody
    public DataTablesOutputDTOListWrapper<Dish> getMenusDishes(@RequestParam int id) {
        DataTablesOutputDTOListWrapper data = new DataTablesOutputDTOListWrapper<>();
        data.setData(menuService.getById(id).getDishes());
        System.out.println("menu: "+menuService.getById(id));
        List<Dish> dishes = menuService.getById(id).getDishes();
        for (Dish dish : dishes) {
            System.out.println(dish);
        }
        return data;
    }

}
