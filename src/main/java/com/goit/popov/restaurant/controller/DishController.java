package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.json.DishWrapper;
import com.goit.popov.restaurant.service.DishService;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOCollectionWrapper;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOUniversal;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

/**
 * Created by Andrey on 1/4/2017.
 */
@Controller
public class DishController {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(DishController.class);

        private static final String CONSTRAINT_VIOLATION_MESSAGE = "Constraint violation error!";

        @Autowired
        private DishService dishService;

        @GetMapping(value = "/admin/dishes")
        public String showDishes() {
                return "th/manager/dishes";
        }

        @GetMapping(value = "/admin/new_dish")
        public ModelAndView showDishForm() {
                Dish dish = new Dish();
                return new ModelAndView("th/manager/new_dish", "dish", dish);
        }

        @PostMapping("/admin/get_dish")
        @ResponseBody
        public Dish getDish(@RequestParam Long dishId) {
                return dishService.getById(dishId);
        }

        @PostMapping(value = { "/all/get_all_dishes", "/waiter/get_dishes" })
        @ResponseBody
        public DataTablesOutputDTOUniversal<Dish> getDishes(DataTablesInputExtendedDTO input) {
                DataTablesOutputDTOUniversal<Dish> data = dishService.getAll(input);
                return data;
        }

        @GetMapping("/all/show_ingredients")
        @ResponseBody
        public ModelAndView getDishes(@RequestParam Long id, ModelAndView modelAndView) {
                modelAndView.addObject("id", dishService.getById(id).getId());
                modelAndView.addObject("dish", dishService.getById(id).getName());
                modelAndView.setViewName("th/all/dishs_ingredients");
                return modelAndView;
        }

        @PostMapping("/all/get_dishs_ingredients")
        @ResponseBody
        public DataTablesOutputDTOCollectionWrapper getDishIngredients(@RequestParam Long dishId) {
                DataTablesOutputDTOCollectionWrapper data = new DataTablesOutputDTOCollectionWrapper();
                data.setData(dishService.toJSON(dishService.getById(dishId).getIngredients()));
                return data;
        }

        @PostMapping(value = "/admin/save_dish")
        public String saveDish(@Valid @ModelAttribute("dish") Dish dish, BindingResult result, RedirectAttributes ra) {
                if (result.hasErrors()) {
                        LOGGER.info("Errors number while saving new dish: " +
                                result.getFieldErrorCount());
                        return "th/manager/new_dish";
                }
                try {
                        dishService.insert(dish);
                } catch (Exception e) {
                        ra.addFlashAttribute("status", HttpStatus.FORBIDDEN);
                        ra.addFlashAttribute("error", CONSTRAINT_VIOLATION_MESSAGE);
                        ra.addFlashAttribute("message", "Error: failed to insert a dish #" + dish);
                        return "redirect:/error";
                }
                return "redirect:/admin/dishes";
        }

        @GetMapping(value = "/admin/edit_dish")
        public String showDishEditForm(@RequestParam("dishId") Long dishId, ModelMap map, HttpSession session) {
                Dish dish = dishService.getById(dishId);
                map.addAttribute("dish", dish);
                session.setAttribute("ingredients", dish.getIngredients());
                return "th/manager/edit_dish";
        }

        @GetMapping(value = "/admin/edit_dishs_ingredients")
        public String showDishsIngredientsEditForm(@RequestParam("dishId") int dishId) {
                return "th/manager/edit_dishs_ingredients";
        }

        @PostMapping(value = "/admin/update_dish")
        public String updateDish(@Valid @ModelAttribute("dish") Dish dish, BindingResult result,
                                 RedirectAttributes ra, HttpSession session) {
                if (result.hasErrors()) {
                        LOGGER.info("Errors number while updating dish: " +
                                result.getFieldErrorCount());
                        return "th/manager/edit_dish";
                }
                try {
                        Map<Ingredient, Double> ingredients = (Map<Ingredient, Double>)
                                session.getAttribute("ingredients");
                        dish.setIngredients(ingredients);
                        dishService.update(dish);
                        LOGGER.info("Dish updated: "+dish);
                } catch (Exception e) {
                        ra.addFlashAttribute("status", HttpStatus.FORBIDDEN);
                        ra.addFlashAttribute("error", CONSTRAINT_VIOLATION_MESSAGE);
                        ra.addFlashAttribute("message", "Error: failed to update the dish #" + dish);
                        return "redirect:/error";
                }
                return "redirect:/admin/dishes";
        }

        @PostMapping(value = "/admin/update_dishs_ingredients")
        public ResponseEntity updateDishsIngredients(@RequestBody DishWrapper dishWrapper, @RequestParam("dishId") Long dishId) {
                try {
                        dishService.updateDishsIngredients(dishId, dishWrapper.getIngredients());
                } catch (Exception e) {
                        LOGGER.error("Error: failure updating Dish!");
                        return new ResponseEntity("Failure updating Dish!",
                                HttpStatus.FORBIDDEN);
                }
                return new ResponseEntity("{\"Result\": \"Success\"}", HttpStatus.OK);
        }

        @RequestMapping(value = "/admin/delete_dish/{id}", method = RequestMethod.GET)
        public String deleteDish(@PathVariable Long id, RedirectAttributes ra) {
                try {
                        dishService.deleteById(id);
                } catch (Exception e) {
                        ra.addFlashAttribute("status", HttpStatus.FORBIDDEN);
                        ra.addFlashAttribute("error", CONSTRAINT_VIOLATION_MESSAGE);
                        ra.addFlashAttribute("message", "Error: failed to deleteById the dish #" + id);
                        return "redirect:/error";
                }
                return "redirect:/admin/dishes";
        }
}
