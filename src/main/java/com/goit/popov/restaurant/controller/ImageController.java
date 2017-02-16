package com.goit.popov.restaurant.controller;

import com.goit.popov.restaurant.model.Employee;
import com.goit.popov.restaurant.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Andrey on 11/26/2016.
 */
@Controller
@RequestMapping("/img")
public class ImageController {

        @Autowired
        private EmployeeService employeeService;

        @RequestMapping(value = "/photo", method = RequestMethod.GET)
        public void showPhoto(@RequestParam("id") Long id,
                              HttpServletResponse response, HttpServletRequest request) throws IOException {
                Employee employee = employeeService.getById(id);
                response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
                response.getOutputStream().write(employee.getPhoto());
                response.getOutputStream().close();
        }
}
