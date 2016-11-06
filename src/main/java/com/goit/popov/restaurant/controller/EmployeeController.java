package com.goit.popov.restaurant.controller;

import com.goit.popov.restaurant.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

        @RequestMapping(value = "/employees", method = RequestMethod.GET)
        public String employees(Map<String, Object> model) {
                model.put("employees", employeeService.getEmployees());
                return "employees";
        }

        @RequestMapping(value = "/employee", method = RequestMethod.GET)
        public ModelAndView employee(@RequestParam("employee") int employee) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("employee", employeeService.getEmployeeById(employee));
                modelAndView.setViewName("employee");
                return modelAndView;
        }

}
