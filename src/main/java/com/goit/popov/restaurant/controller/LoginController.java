package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Employee;
import com.goit.popov.restaurant.service.EmployeeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 1/7/2017.
 */
@Controller
public class LoginController {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "th/login";
    }

    @PostMapping("/try_login")
    public ModelAndView getEmployee(@RequestParam String login, @RequestParam String password, ModelAndView modelAndView) {
        try {
            Employee employee = employeeService.getEmployeeByLoginAndPassword(login, password);
            if (employee==null) throw new RuntimeException("No such an employee!");
            logger.info("Employee: "+employee);
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add( new SimpleGrantedAuthority(employee.getRole()));
            UserDetails ud  = new org.springframework.security.core.userdetails.User(employee.getLogin(), employee.getPassword(),
                    true, true, true, true, authorities);
        } catch (Exception e) {
            logger.error("ERROR: "+e.getMessage());
            modelAndView.addObject("error", e.getMessage());
        }
        modelAndView.setViewName("th/login");
        return modelAndView;
    }
}
