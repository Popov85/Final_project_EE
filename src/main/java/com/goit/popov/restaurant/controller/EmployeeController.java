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

        @Autowired
        private ApplicationContext applicationContext;

        static Logger logger = (Logger) LoggerFactory.getLogger(EmployeeController.class);

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
                positionsList.put("-1", "Select Position");
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
                logger.info("Show Employee Form...");
                return new ModelAndView("new_employee","employee",new Employee());
        }

        // Create
        @RequestMapping(value="/save_employee",method = RequestMethod.POST)
        public ModelAndView saveEmployee(@RequestParam("position") String position,
                                        @RequestParam("name") String name, @RequestParam("phone") String phone,
                                        @RequestParam("dob") Date dob, @RequestParam("salary") BigDecimal salary){
                // Bean name is: Waiter - > WaiterService (save ())
                EmployeeService employee = (EmployeeService) applicationContext.getBean(position);
                employee.save(name, dob, phone, salary, position);
                return new ModelAndView("redirect:/employees");
        }

        // Read (update form)
        /*@RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
        public String edit(@PathVariable("id") int id, Model model){
                //populatePositions();
                model.addAttribute("positions", positionService.getAll());
                model.addAttribute("employee", employeeService.getEmployeeById(id));
                return "update_employee";
        }*/

        @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
        public ModelAndView showEmployeeEditForm(@PathVariable("id") int id){
                logger.info("Show Employee Edit Form...");
                ModelAndView modelAndView = new ModelAndView("update_employee","employee",employeeService.getEmployeeById(id));
                return modelAndView;
        }

        // Update
        /*@RequestMapping(value="/update_employee",method = RequestMethod.POST)
        public ModelAndView editSave(@ModelAttribute("employee") Employee employee){
                employeeService.update(employee);
                return new ModelAndView("redirect:/employees");
        }*/

        @RequestMapping(value="/update_employee",method = RequestMethod.POST)
        public ModelAndView updateEmployee(@RequestParam("id") int id, @RequestParam("position") String position,
                                         @RequestParam("name") String name, @RequestParam("phone") String phone,
                                         @RequestParam("dob") Date dob, @RequestParam("salary") BigDecimal salary){
                // Bean name is: Waiter - > WaiterService (save ())
                EmployeeService employee = (EmployeeService) applicationContext.getBean(position);
                employee.update(id, name, dob, phone, salary, position);
                return new ModelAndView("redirect:/employees");
        }

        // Delete
        @RequestMapping(value="/deleteemp/{id}",method = RequestMethod.GET)
        public ModelAndView delete(@PathVariable int id){
                employeeService.deleteById(id);
                return new ModelAndView("redirect:/employees");
        }
}
