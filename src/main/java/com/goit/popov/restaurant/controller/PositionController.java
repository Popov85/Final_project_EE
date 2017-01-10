package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Position;
import com.goit.popov.restaurant.service.PositionService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.io.IOException;
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

        @GetMapping("/admin/new_position")
        public ModelAndView showPositionForm(){
                logger.info("Show Position form");
                Position position = new Position();
                position.setName("");
                return new ModelAndView("th/new_position", "position", position);
        }

        @GetMapping("/admin/positions")
        public String getPositions(Map<String, Object> model) {
                model.put("positions", positionService.getAll());
                logger.info("Display all positions");
                return "th/positions";
        }

        @PostMapping(value="/save_position")
        public String savePosition(@Valid @ModelAttribute("position") Position position, BindingResult result){
                if (result.hasErrors()) {
                        logger.info("Errors are present");
                        logger.info("Errors number: "+result.getFieldErrorCount());
                        logger.info("Error in name is: "+result.getFieldError("name"));
                        return "redirect:/new_position";
                } else {
                        positionService.save(position);
                        logger.info("Success saved: OK");
                        return "redirect:/admin/positions";
                }
        }

        // Read (update form)
        @GetMapping(value = "/admin/edit_position/{id}")
        public String showPositionEditForm(@PathVariable("id") int id, ModelMap map){
                logger.info("Show form Position for updating "+id);
                Position position = positionService.getById(id);
                map.addAttribute("position", position);
                return "th/update_position";
        }

        // Update
        // http://stackoverflow.com/questions/14938344/thymeleaf-construct-url-with-variable
        @PostMapping(value="/update_position")
        public String editSave(@Valid @ModelAttribute("position") Position position, BindingResult result){
                logger.info("Updating "+position);
                if (result.hasErrors()) {
                        logger.info("Errors number: "+result.getFieldErrorCount());
                        return "th/update_position";
                } else {
                        positionService.update(position);
                        return "redirect:/admin/positions";
                }
        }

        // Delete
        @RequestMapping(value="/admin/delete_position/{id}",method = RequestMethod.GET)
        public ModelAndView delete(@PathVariable int id){
                positionService.deleteById(id);
                return new ModelAndView("jsp/positions");
        }

//--------------------------------- AJAX------------------------------------

        @GetMapping("/new_position_ajax")
        public ModelAndView showPositionAjaxForm(){
                logger.info("Show Position Ajax form");
                Position position = new Position();
                position.setName("");
                return new ModelAndView("th/new_position_ajax", "position", position);
        }

        /*@PostMapping(value="/create_position_ajax",
                produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
        @ResponseBody
        public String createPosition(@RequestBody String position) {
                logger.info("Position: "+position);
                return "{"+"\""+"result"+"\""+":"+"\""+"success"+"\""+"}";

        }*/

        @PostMapping(value="/create_position_ajax")
        public @ResponseBody Position createPosition(@RequestBody Position position) throws IOException {
                logger.info("Position: "+position);
                /*ObjectMapper mapper = new ObjectMapper();
                Position p = mapper.readValue(position, Position.class);
                logger.info("Converted :"+p);*/
                Position p = new Position();
                p.setId(1000000);
                p.setName(position.getName());
                return p;
        }
}

