package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.*;
import com.goit.popov.restaurant.service.EmployeeService;
import com.goit.popov.restaurant.service.PositionService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 11/6/2016.
 */
@Controller
public class EmployeeController {

        private EmployeeService employeeService;
        private PositionService positionService;

        static Logger logger = (Logger) LoggerFactory.getLogger(EmployeeController.class);

        @Autowired
        public void setPositionService(PositionService positionService) {
                this.positionService = positionService;
        }

        @Autowired
        public void setEmployeeService(EmployeeService employeeService) {
                this.employeeService = employeeService;
        }

        @RequestMapping("/new_employee")
        public ModelAndView showEmployeeForm(){
                logger.info("Show Employee Form...");
                return new ModelAndView("new_waiter","employee",new Employee());
        }


        // Create
        @RequestMapping(value="/save_employee",method = RequestMethod.POST)
        public ModelAndView saveWaiter2(@RequestParam("id") int id, @RequestParam("position") int position,
                                        @RequestParam("name") String name, @RequestParam("phone") String phone,
                                        @RequestParam("dob") Date dob, @RequestParam("salary") BigDecimal salary){
                if (id==0) {
                        logger.info("New employee coming ------------------------------------------: ");
                } else {
                        logger.info("Edit existing employee ------------------------------------------: "+id);
                }

                //employeeService
                return new ModelAndView("redirect:/employees");
        }

        @ModelAttribute("positionsList")
        public Map<Integer, String> populatePositions() {
                List<Position> positions = positionService.getAll();
                Map<Integer, String> positionsList = new HashMap<>();
                positionsList.put(-1, "Select Position");
                for (Position position : positions) {
                       positionsList.put(position.getId(), position.getName());
                }
                return positionsList;
        }
















        @InitBinder
        public void initBinder(WebDataBinder binder) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                sdf.setLenient(true);
                binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        }

        // Get All
        @RequestMapping(value = "/employees", method = RequestMethod.GET)
        public String employees(Map<String, Object> model) {
                model.put("employees", employeeService.getEmployees());
                return "employees";
        }



        // Edit
        @RequestMapping(value = "/employee", method = RequestMethod.GET)
        public ModelAndView employee(@RequestParam("employee") int employee) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("employee", employeeService.getEmployeeById(employee));
                modelAndView.setViewName("employee");
                return modelAndView;
        }

        // Update
        @RequestMapping(value="/update",method = RequestMethod.POST)
        public ModelAndView editSave(@ModelAttribute("employee") Employee employee){
                employeeService.update(employee);
                return new ModelAndView("redirect:/employees");
        }

        @RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
        public String edit(@PathVariable("id") int id, Model model){
                model.addAttribute("employee", employeeService.getEmployeeById(id));
                return "employee";
        }

        // Delete
        @RequestMapping(value="/deleteemp/{id}",method = RequestMethod.GET)
        public ModelAndView delete(@PathVariable int id){
                employeeService.deleteById(id);
                return new ModelAndView("redirect:/employees");
        }
}
