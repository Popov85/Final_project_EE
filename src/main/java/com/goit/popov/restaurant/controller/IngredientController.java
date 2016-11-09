package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.service.IngredientService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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

        @RequestMapping("/new_ingredient")
        public ModelAndView showform(){
                System.out.println("show ingredient form");
                logger.info("show ingredient form");
                return new ModelAndView("ingredient","ingredient",new Ingredient());
        }

        // Create
        @RequestMapping(value="/save_ingredient",method = RequestMethod.POST)
        public ModelAndView save(@ModelAttribute("ingredient") Ingredient ingredient){
                System.out.println("My ingredient is: "+ingredient);
                logger.info(ingredient.toString());
                ingredientService.save(ingredient);
                return new ModelAndView("redirect:/employees");
        }

}
