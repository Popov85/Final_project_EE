package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Position;
import com.goit.popov.restaurant.service.PositionService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;


/**
 * Created by Andrey on 11/28/2016.
 */
@Controller
public class PositionController {

        static Logger logger = (Logger) LoggerFactory.getLogger(PositionController.class);

        private static final String DELETION_FAILURE_MESSAGE = "Failed to delete the position: id =";

        @Autowired
        private PositionService positionService;

        @GetMapping("/admin/new_position")
        public ModelAndView showPositionForm(){
                Position position = new Position();
                position.setName("");
                return new ModelAndView("th/new_position", "position", position);
        }

        @GetMapping("/admin/positions")
        public String getPositions(Map<String, Object> model) {
                model.put("positions", positionService.getAll());
                return "th/positions";
        }

        @PostMapping(value="/admin/save_position")
        public String savePosition(@Valid @ModelAttribute("position") Position position, BindingResult result){
                if (result.hasErrors()) {
                        logger.info("Errors number while saving new position: "+
                                result.getFieldErrorCount());
                        return "th/new_position";
                }
                positionService.save(position);
                return "redirect:/admin/positions";
        }

        // Read (update form)
        @GetMapping(value = "/admin/edit_position/{id}")
        public String showPositionEditForm(@PathVariable("id") int id, ModelMap map){
                Position position = positionService.getById(id);
                map.addAttribute("position", position);
                return "th/update_position";
        }

        // Update
        @PostMapping(value="/admin/update_position")
        public String editSave(@Valid @ModelAttribute("position") Position position, BindingResult result){
                if (result.hasErrors()) {
                        logger.info("Errors number while updating position: "+
                                result.getFieldErrorCount());
                        return "th/update_position";
                } else {
                        positionService.update(position);
                        return "redirect:/admin/positions";
                }
        }

        // Delete
        @RequestMapping(value="/admin/delete_position/{id}",method = RequestMethod.GET)
        public String delete(@PathVariable int id, RedirectAttributes ra){
                try {
                        positionService.deleteById(id);
                } catch (Exception e) {
                        ra.addFlashAttribute("status", HttpStatus.FORBIDDEN);
                        ra.addFlashAttribute("error", "Constraint violation exception deleting position!");
                        ra.addFlashAttribute("message", DELETION_FAILURE_MESSAGE +id);
                        logger.info("Constraint violation exception deleting position: "+e.getMessage()+
                                "/ exception name is: "+ e.getClass());
                        return "redirect:/error";
                }
                return "redirect:/admin/positions";
        }
}

