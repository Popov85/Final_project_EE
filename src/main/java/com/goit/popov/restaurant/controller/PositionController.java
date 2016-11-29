package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Position;
import com.goit.popov.restaurant.service.PositionService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.Map;


/**
 * Created by Andrey on 11/28/2016.
 */
@Controller
public class PositionController {

        static Logger logger = (Logger) LoggerFactory.getLogger(PositionController.class);

        @Autowired
        private PositionService positionService;

        public void setPositionService(PositionService positionService) {
                this.positionService = positionService;
        }

        @GetMapping("/new_position")
        public ModelAndView showPositionForm(){
                logger.info("Show Position form");
                Position position = new Position();
                position.setName("");
                return new ModelAndView("th/new_position", "position", position);
        }

        // Get All
        /*@GetMapping(value = "th/positions")
        @ModelAttribute("positions")
        public List<Position> positions() {
                logger.info("Display all positions");
                return positionService.getAll();
        }*/

        @GetMapping("/positions")
        public String getPositions(Map<String, Object> model) {
                model.put("positions", positionService.getAll());
                logger.info("Display all positions");
                return "th/positions";
        }

        @PostMapping(value="/save_position")
        public ModelAndView savePosition(@Valid @ModelAttribute("position") Position position, BindingResult result){
                if (result.hasErrors()) {
                        logger.info("Errors are present");
                        logger.info("Errors number: "+result.getFieldErrorCount());
                        logger.info("Error in name is: "+result.getFieldError("name"));
                        return new ModelAndView("th/new_position","position", position);
                } else {
                        positionService.save(position);
                        logger.info("Success saved: OK");
                        return new ModelAndView("th/positions","positions", positionService.getAll());
                }
        }
}

