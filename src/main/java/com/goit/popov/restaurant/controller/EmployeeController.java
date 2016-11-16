package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.*;
import com.goit.popov.restaurant.service.EmployeeService;
import com.goit.popov.restaurant.service.PositionService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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

        private static final Logger logger = (Logger) LoggerFactory.getLogger(EmployeeController.class);

        private EmployeeService employeeService;
        private PositionService positionService;

        @Autowired
        private ApplicationContext applicationContext;

        @Autowired
        public void setPositionService(PositionService positionService) {
                this.positionService = positionService;
        }

        @Autowired
        public void setEmployeeService(EmployeeService employeeService) {
                this.employeeService = employeeService;
        }

        // Populate positions
        @ModelAttribute("positions")
        public Map<String, String> populatePositions() {
                List<Position> positions = positionService.getAll();
                Map<String, String> positionsList = new HashMap<>();
                positionsList.put("Unknown", "Select Position");
                for (Position position : positions) {
                        positionsList.put(position.getName(), position.getName());
                }
                return positionsList;
        }

        // Date settings
        @InitBinder
        public void initBinder(WebDataBinder binder) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                sdf.setLenient(true);
                binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
        }

        // Read All
        @RequestMapping(value = "/employees", method = RequestMethod.GET)
        public String employees(Map<String, Object> model) {
                model.put("employees", employeeService.getEmployees());
                return "employees";
        }

        // New form
        @RequestMapping("/new_employee")
        public ModelAndView showEmployeeForm(){
                return new ModelAndView("new_employee","employee",new Employee());
        }

        // Create
        @RequestMapping(value="/save_employee",method = RequestMethod.POST)
        public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result,
                                   @RequestParam("position") String position){
                if (result.hasErrors()) {
                        return "new_employee";
                }
                // Bean name is: Waiter - > WaiterService (save ())
                EmployeeService employeeService = (EmployeeService) applicationContext.getBean(position);
                employeeService.save(employee);
                return "redirect:/employees";
        }

        // Read (update form)
        @RequestMapping(value = "/edit_employee/{id}", method = RequestMethod.GET)
        public String showEmployeeEditForm(@PathVariable("id") int id, ModelMap map){
                Employee employee = employeeService.getEmployeeById(id);
                map.addAttribute("employee", employee);
                map.addAttribute("positions", populatePositions());
                map.addAttribute("position", employee.getPosition().getName());
                return "update_employee";
        }

        // Update
        @RequestMapping(value="/update_employee", method = RequestMethod.POST)
        public String updateEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result,
                                           @RequestParam("position") String position){
                if (result.hasErrors()) {
                        logger.info("# of errors is: "+result.getFieldErrorCount());
                        return "update_employee";
                }
                EmployeeService employeeService = (EmployeeService) applicationContext.getBean(position);
                employeeService.update(employee);
                return "redirect:/employees";
        }

        // Delete
        @RequestMapping(value="/delete_employee/{id}",method = RequestMethod.GET)
        public ModelAndView delete(@PathVariable int id){
                employeeService.deleteById(id);
                return new ModelAndView("redirect:/employees");
        }
}
