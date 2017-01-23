package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.Unit;
import com.goit.popov.restaurant.service.IngredientService;
import com.goit.popov.restaurant.service.UnitService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 09.11.2016.
 */
@Controller
public class IngredientController {

        static Logger logger = (Logger) LoggerFactory.getLogger(IngredientController.class);

        @Autowired
        private IngredientService ingredientService;

        public void setIngredientService(IngredientService ingredientService) {
                this.ingredientService = ingredientService;
        }

        @Autowired
        private UnitService unitService;

        public void setUnitService(UnitService unitService) {
                this.unitService = unitService;
        }

        // Populate units
        @ModelAttribute("units")
        public Map<Integer, String> populatePositions() {
                List<Unit> units = unitService.getAll();
                Map<Integer, String> unitsList = new HashMap<>();
                unitsList.put(-1, "Select Unit");
                for (Unit unit : units) {
                        unitsList.put(unit.getId(), unit.getName());
                }
                return unitsList;
        }

        // Show form
        @RequestMapping("/new_ingredient")
        public ModelAndView showIngredientForm(){
                return new ModelAndView("jsp/new_ingredient","ingredient",new Ingredient());
        }

        // Get All
        @RequestMapping(value = "/ingredients", method = RequestMethod.GET)
        public String ingredients(Map<String, Object> model) {
                model.put("ingredients", ingredientService.getAll());
                return "jsp/ingredients";
        }

        // Get All
        /*@RequestMapping(value = "/ingredients", method = RequestMethod.GET)
        @ModelAttribute("ingredients")
        public List<Ingredient> ingredients() {
                return ingredientService.getAllOrders();
        }*/

        // Create
        @RequestMapping(value="/save_ingredient",method = RequestMethod.POST)
        public String saveIngredient(@Valid @ModelAttribute("ingredient") Ingredient ingredient, BindingResult result){
                if (result.hasErrors()) {
                        logger.info("Errors on the form!");
                        return "new_ingredient";
                } else {
                        logger.info("Ingredient to be saved is: "+ingredient);
                        ingredientService.save(ingredient);
                        return "redirect:/ingredients";
                }
        }

        /* It displays object data into form for the given id. The @PathVariable puts URL data into variable.*/
        @RequestMapping(value="/update_ingredient/{id}", method = RequestMethod.GET)
        public ModelAndView edit(@PathVariable int id){
                logger.info("Show data "+id);
                Ingredient ing=ingredientService.getIngredientById(id);
                return new ModelAndView("jsp/update_ingredient","ingredient",ing);
        }

        // Update
        @RequestMapping(value="/update_ingredient/{id}", method = RequestMethod.POST)
        public ModelAndView editSave(@ModelAttribute Ingredient ingredient, @PathVariable Integer id){
                logger.info("Updating");
                ModelAndView modelAndView = new ModelAndView("jsp/ingredients");
                ingredientService.update(ingredient);
                return modelAndView;
        }

        // Delete
        @RequestMapping(value="/delete_ingredient/{id}",method = RequestMethod.GET)
        public ModelAndView delete(@PathVariable int id){
                ingredientService.deleteById(id);
                return new ModelAndView("jsp/ingredients");
        }

}
