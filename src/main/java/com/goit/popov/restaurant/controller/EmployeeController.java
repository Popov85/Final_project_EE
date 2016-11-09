package com.goit.popov.restaurant.controller;

import com.goit.popov.restaurant.model.Employee;
import com.goit.popov.restaurant.model.Waiter;
import com.goit.popov.restaurant.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
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

        @RequestMapping("/new_waiter")
        public ModelAndView showform(){
                System.out.println("show form");
                return new ModelAndView("new_waiter","waiter",new Waiter());
        }

        // Create
        @RequestMapping(value="/save",method = RequestMethod.POST)
        public ModelAndView save(@ModelAttribute("waiter") Employee waiter){
                System.out.println("My employee is: "+waiter);
                employeeService.save(waiter);
                return new ModelAndView("redirect:/employees");
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
