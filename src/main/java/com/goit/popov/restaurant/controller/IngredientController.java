package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.Unit;
import com.goit.popov.restaurant.service.IngredientService;
import com.goit.popov.restaurant.service.UnitService;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOListWrapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 09.11.2016.
 */
@Controller
public class IngredientController {

        static Logger logger = (Logger) LoggerFactory.getLogger(IngredientController.class);

        private static final String CONSTRAINT_VIOLATION_MESSAGE = "Constraint violation error!";

        @Autowired
        private IngredientService ingredientService;

        @Autowired
        private UnitService unitService;

        @ModelAttribute("units")
        public List<Unit> populatePositions() {
                List<Unit> units = unitService.getAll();
                return units;
        }

        @GetMapping("/admin/new_ingredient")
        public ModelAndView showIngredientForm() {
                return new ModelAndView("th/manager/new_ingredient", "ingredient", new Ingredient());
        }

        @RequestMapping(value = "/admin/ingredients", method = RequestMethod.GET)
        public String ingredients(Map<String, Object> model) {
                model.put("ingredients", ingredientService.getAll());
                return "th/manager/ingredients";
        }

        @PostMapping("/admin/get_all_ingredients")
        @ResponseBody
        public DataTablesOutputDTOListWrapper<Ingredient> getAllIngredients() {
                DataTablesOutputDTOListWrapper<Ingredient> ingredients = new DataTablesOutputDTOListWrapper<>();
                ingredients.setData(ingredientService.getAll());
                return ingredients;
        }

        @RequestMapping(value = "/admin/save_ingredient", method = RequestMethod.POST)
        public String saveIngredient(@Valid @ModelAttribute("ingredient") Ingredient ingredient, BindingResult result, Model model) {
                if (result.hasErrors()) {
                        logger.error("Errors(" + result.getErrorCount() + "): during creating!");
                        return "th/manager/new_ingredient";
                }
                try {
                        ingredientService.insert(ingredient);
                } catch (Exception e) {
                        logger.error("ERROR: "+e.getCause());
                        model.addAttribute("constraintViolationError",CONSTRAINT_VIOLATION_MESSAGE);
                        return "th/manager/new_ingredient";
                }
                return "redirect:/admin/ingredients";
        }

        @RequestMapping(value = "/admin/edit_ingredient/{id}", method = RequestMethod.GET)
        public ModelAndView edit(@PathVariable Long id) {
                Ingredient ing = ingredientService.getById(id);
                return new ModelAndView("th/manager/edit_ingredient", "ingredient", ing);
        }

        @RequestMapping(value = "/admin/update_ingredient", method = RequestMethod.POST)
        public String editSave(@Valid @ModelAttribute Ingredient ingredient, BindingResult result, Model model) {
                if (result.hasErrors()) {
                        logger.error("Errors(" + result.getErrorCount() + "): during updating!");
                        return "th/manager/edit_ingredient";
                }
                try {
                        ingredientService.update(ingredient);
                } catch (Exception e) {
                        logger.error("ERROR: "+e.getCause());
                        model.addAttribute("constraintViolationError", CONSTRAINT_VIOLATION_MESSAGE);
                        return "th/admin/edit_ingredient";
                }
                return "redirect:/admin/ingredients";
        }

        @RequestMapping(value = "/admin/delete_ingredient/{id}", method = RequestMethod.GET)
        public String delete(@PathVariable Long id, RedirectAttributes ra) {
                try {
                        ingredientService.deleteById(id);
                } catch (Exception e) {
                        ra.addFlashAttribute("status", HttpStatus.FORBIDDEN);
                        ra.addFlashAttribute("error", CONSTRAINT_VIOLATION_MESSAGE);
                        ra.addFlashAttribute("message", "Error: failed to deleteById the ingredient #" + id);
                        return "redirect:/error";
                }
                return "redirect:/admin/ingredients";
        }
}
