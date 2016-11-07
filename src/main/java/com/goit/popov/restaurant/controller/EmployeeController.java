package com.goit.popov.restaurant.controller;

import com.goit.popov.restaurant.model.Employee;
import com.goit.popov.restaurant.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by Andrey on 11/6/2016.
 */
@Controller
public class EmployeeController {
        private EmployeeService employeeService;

        @Autowired
        public void setEmployeeService(EmployeeService employeeService) {
                this.employeeService = employeeService;
        }

        @RequestMapping("/employeeForm")
        public ModelAndView showform(){
                return new ModelAndView("employeeForm","command",new Employee());
        }

        // Get All
        @RequestMapping(value = "/employees", method = RequestMethod.GET)
        public String employees(Map<String, Object> model) {
                model.put("employees", employeeService.getEmployees());
                return "employees";
        }

        // Create
        @RequestMapping(value="/save",method = RequestMethod.POST)
        public ModelAndView save(@ModelAttribute("emp") Employee employee){
                employeeService.save(employee);
                return new ModelAndView("redirect:/employees");
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
        @RequestMapping(value="/editsave",method = RequestMethod.POST)
        public ModelAndView editsave(@ModelAttribute("emp") Employee employee){
                employeeService.update(employee);
                return new ModelAndView("redirect:/employees");
        }

        // Delete
        @RequestMapping(value="/deleteemp/{id}",method = RequestMethod.GET)
        public ModelAndView delete(@PathVariable int id){
                employeeService.deleteById(id);
                return new ModelAndView("redirect:/employees");
        }
}
