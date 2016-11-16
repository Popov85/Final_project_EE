package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.service.IngredientService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by Andrey on 09.11.2016.
 */
@Controller
public class IngredientController {
        @Autowired
        private IngredientService ingredientService;

        public void setIngredientService(IngredientService ingredientService) {
                this.ingredientService = ingredientService;
        }

        static Logger logger = (Logger) LoggerFactory.getLogger(IngredientController.class);

        // Show form
        @RequestMapping("/new_ingredient")
        public ModelAndView showIngredientForm(){
                logger.info("show ingredient form");
                return new ModelAndView("new_ingredient","ingredient",new Ingredient());
        }

        // Get All
        @RequestMapping(value = "/ingredients", method = RequestMethod.GET)
        public String ingredients(Map<String, Object> model) {
                model.put("ingredients", ingredientService.getAll());
                return "ingredients";
        }

        // Create
        @RequestMapping(value="/save_ingredient",method = RequestMethod.POST)
        public String saveIngredient(@Valid @ModelAttribute("ingredient") Ingredient ingredient, BindingResult result){
                if (result.hasErrors()) {
                        return "new_ingredient";
                } else {
                        ingredientService.save(ingredient);
                        return "redirect:/ingredients";
                }
        }

        /* It displays object data into form for the given id. The @PathVariable puts URL data into variable.*/
        @RequestMapping(value="/update_ingredient/{id}", method = RequestMethod.GET)
        public ModelAndView edit(@PathVariable int id){
                logger.info("Show data "+id);
                Ingredient ing=ingredientService.getIngredientById(id);
                return new ModelAndView("update_ingredient","ingredient",ing);
        }

        // Update
        @RequestMapping(value="/update_ingredient/{id}", method = RequestMethod.POST)
        public ModelAndView editSave(@ModelAttribute Ingredient ingredient, @PathVariable Integer id){
                logger.info("Updating");
                ModelAndView modelAndView = new ModelAndView("redirect:/ingredients");
                ingredientService.update(ingredient);
                return modelAndView;
        }

        // Delete
        @RequestMapping(value="/delete_ingredient/{id}",method = RequestMethod.GET)
        public ModelAndView delete(@PathVariable int id){
                ingredientService.deleteById(id);
                return new ModelAndView("redirect:/ingredients");
        }

}
