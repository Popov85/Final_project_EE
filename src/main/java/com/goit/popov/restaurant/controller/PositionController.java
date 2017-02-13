package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Position;
import com.goit.popov.restaurant.service.PositionService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;
import java.util.Map;

/**
 * Created by Andrey on 11/28/2016.
 */
@Controller
public class PositionController {

        static Logger logger = (Logger) LoggerFactory.getLogger(PositionController.class);

        private static final String FORBIDDEN_ACTION_MESSAGE = "You cannot perform this operation!";
        private static final String UNIQUE_CONSTRAINT_MESSAGE = "The value entered is not unique!";
        private static final String ANY_CONSTRAINT_MESSAGE = "Constraint violation error!";

        @Autowired
        private PositionService positionService;

        @GetMapping("/admin/new_position")
        public ModelAndView showPositionForm(){
                return new ModelAndView("th/manager/new_position", "position", new Position());
        }

        @GetMapping("/admin/positions")
        public String getPositions(Map<String, Object> model) {
                model.put("positions", positionService.getAll());
                return "th/manager/positions";
        }

        @PostMapping(value="/admin/save_position")
        public String savePosition(@Valid @ModelAttribute("position") Position position, BindingResult result, Model model){
                if (result.hasErrors()) {
                        logger.error("Errors number while saving new position: "+
                                result.getFieldErrorCount());
                        return "th/manager/new_position";
                }
                try {
                        positionService.save(position);
                } catch (DataIntegrityViolationException e) {
                        logger.error("ERROR: "+e.getMessage()+" cause: "+e.getClass());
                        model.addAttribute("error", UNIQUE_CONSTRAINT_MESSAGE);
                        return "th/manager/new_position";
                } catch (Exception e) {
                        logger.error("ERROR: "+e.getMessage()+" cause: "+e.getClass());
                        model.addAttribute("error", FORBIDDEN_ACTION_MESSAGE);
                        return "th/manager/new_position";
                }
                return "redirect:/admin/positions";
        }

        @GetMapping(value = "/admin/edit_position/{id}")
        public String showPositionEditForm(@PathVariable("id") int id, ModelMap map, RedirectAttributes ra){
                try {
                        if (!positionService.isPossibleOperation(id))
                                throw new UnsupportedOperationException("You cannot change privileges of this position");
                } catch (UnsupportedOperationException e) {
                        ra.addFlashAttribute("error", e.getMessage());
                        return "redirect:/error";
                }
                Position position = positionService.getById(id);
                map.addAttribute("position", position);
                return "th/manager/update_position";
        }

        @PostMapping(value="/admin/update_position")
        public String updatePosition(@Valid @ModelAttribute("position") Position position, BindingResult result, Model model){
                if (result.hasErrors()) {
                        logger.error("Errors number while updating position: "+
                                result.getFieldErrorCount());
                        return "th/manager/update_position";
                } else {
                        try {
                                positionService.update(position);
                        } catch (DataIntegrityViolationException e) {
                                logger.error("ERROR: "+e.getMessage()+" cause: "+e.getClass());
                                model.addAttribute("error", UNIQUE_CONSTRAINT_MESSAGE);
                                return "th/manager/update_position";
                        } catch (Exception e) {
                                logger.error("ERROR: "+e.getMessage()+" cause: "+e.getClass());
                                model.addAttribute("error", ANY_CONSTRAINT_MESSAGE);
                                return "th/manager/update_position";
                        }
                        return "redirect:/admin/positions";
                }
        }

        @RequestMapping(value="/admin/delete_position/{id}",method = RequestMethod.GET)
        public String delete(@PathVariable int id, RedirectAttributes ra){
                try {
                        if (!positionService.isPossibleOperation(id))
                                throw new UnsupportedOperationException("You cannot delete this position!");
                        positionService.deleteById(id);
                } catch (UnsupportedOperationException e) {
                        ra.addFlashAttribute("error", e.getMessage());
                        logger.error(FORBIDDEN_ACTION_MESSAGE +e.getMessage()+
                                "/ exception name is: "+ e.getClass());
                        return "redirect:/error";
                } catch (Exception e) {
                        ra.addFlashAttribute("status", HttpStatus.FORBIDDEN);
                        ra.addFlashAttribute("error", "This position seemingly has references!");
                        ra.addFlashAttribute("message", ANY_CONSTRAINT_MESSAGE);
                        logger.error("ERROR: " +e.getMessage()+
                                "/ exception name is: "+ e.getClass());
                        return "redirect:/error";
                }
                return "redirect:/admin/positions";
        }
}

