package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.*;
import com.goit.popov.restaurant.service.EmployeeService;
import com.goit.popov.restaurant.service.PositionService;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

        private static final String NON_UNIQUE_CONSTRAINT_MESSAGE = "Some values on the form are not unique";

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
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(true);
                binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
                binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }

        // Read All
        @GetMapping(value = "/admin/employees")
        public String employeesTh(Map<String, Object> model) {
                model.put("employees", employeeService.getEmployees());
                return "th/manager/employees";
        }

        // New form
        @RequestMapping("/admin/new_employee")
        public ModelAndView showEmployeeForm(){
                return new ModelAndView("jsp/new_employee","employee",new Employee());
        }

        // Create
        @RequestMapping(value="/save_employee",method = RequestMethod.POST)
        public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model,
                                   @RequestParam("position") String position, @RequestParam("photo") MultipartFile photo){
                if (result.hasErrors()) {
                        logger.info("# of errors is: "+result.getFieldErrorCount());
                        return "jsp/new_employee";
                }
                EmployeeService employeeService;
                if ((position.equals("Waiter")) || (position.equals("Chef")) || (position.equals("Manager"))) {
                        employeeService = (EmployeeService) applicationContext.getBean(position);
                } else {
                        employeeService = (EmployeeService) applicationContext.getBean("Employee");
                }
                try {
                        employeeService.save(employee);
                } catch (DataIntegrityViolationException e) {
                        model.addAttribute("constraintViolationError", NON_UNIQUE_CONSTRAINT_MESSAGE);
                        logger.info("Constraint violation exception inserting employee: "+e.getMessage()+
                                " exception name is: "+ e.getClass());
                        return "jsp/new_employee";
                } catch (Throwable e) {
                        model.addAttribute("unexpectedError", e.getMessage());
                        logger.info("Another error inserting employee: "+e.getMessage()+
                                " exception name is: "+ e.getClass());
                        return "jsp/new_employee";
                }
                logger.info("Successfully added employee: "+employee);
                return "redirect:/admin/employees";
        }

        // Read (update form)
        @RequestMapping(value = "/admin/edit_employee/{id}", method = RequestMethod.GET)
        public String showEmployeeEditForm(@PathVariable("id") int id, ModelMap map, HttpSession session){
                Employee employee = employeeService.getEmployeeById(id);
                map.addAttribute("employee", employee);
                map.addAttribute("positions", populatePositions());
                map.addAttribute("position", employee.getPosition().getName());
                session.setAttribute("position",employee.getPosition().getName());
                session.setAttribute("file",employee.getPhoto());
                return "jsp/update_employee";
        }

        // Update
        @RequestMapping(value="/update_employee", method = RequestMethod.POST)
        public String updateEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result,
                                     @RequestParam("position") String newPosition, HttpSession session, Model model,
                                     @RequestParam("photo") MultipartFile photo){
                if (result.hasErrors()) {
                        logger.info("# of errors is: "+result.getFieldErrorCount());
                        return "jsp/update_employee";
                }
                String previousPosition = (String) session.getAttribute("position");
                EmployeeService employeeService;
                if ((newPosition.equals("Waiter")) || (newPosition.equals("Chef")) || (newPosition.equals("Manager"))) {
                        employeeService = (EmployeeService) applicationContext.getBean(newPosition);
                } else {
                        employeeService = (EmployeeService) applicationContext.getBean("Employee");
                }
                if (photo.isEmpty()) employee.setPhoto((byte[]) session.getAttribute("file"));
                try {
                        if (!previousPosition.equals(newPosition)) {
                                logger.info("Position changed! Previous was: "+previousPosition+
                                                "/ new position is: "+newPosition);
                                employeeService.update(employee, true);
                        } else {
                                employeeService.update(employee);
                        }
                } catch (DataIntegrityViolationException e) {
                        model.addAttribute("constraintViolationError", NON_UNIQUE_CONSTRAINT_MESSAGE);
                        logger.info("Constraint violation exception updating employee: "+e.getMessage()+
                                "/ exception name is: "+ e.getClass());
                        return "jsp/update_employee";
                } catch (RuntimeException e) {
                        model.addAttribute("integrityViolationError", e.getMessage());
                        logger.info("Data integrity exception updating employee: "+e.getMessage()+
                                "/ exception name is: "+ e.getClass());
                        return "jsp/update_employee";
                } catch (Throwable e) {
                        model.addAttribute("unexpectedError", e.getMessage());
                        logger.info("Another error updating employee"+e.getMessage()+
                                " exception name is: "+ e.getClass());
                        return "jsp/update_employee";
                }
                logger.info("Successfully updated employee: "+employee);
                return "redirect:/admin/employees";
        }

        // Delete
        @RequestMapping(value="/delete_employee/{id}",method = RequestMethod.GET)
        public ModelAndView delete(@PathVariable int id, RedirectAttributes ra){
                //employeeService.deleteById(id);
                try {
                        employeeService.deleteById(id);
                } catch (PersistenceException e) {
                        ModelAndView mv = new ModelAndView();
                        mv.setViewName("redirect:/error");/*
                        mv.addObject("error", "Constraint violation exception deleting employee!");
                        mv.addObject("message", "Failed to delete an employee: id = "+id);*/
                        ra.addFlashAttribute("error", "Constraint violation exception deleting employee!");
                        ra.addFlashAttribute("message", "Failed to delete an employee: id = "+id);
                        logger.info("Constraint violation exception deleting employee"+e.getMessage()+
                                " exception name is: "+ e.getClass());
                        return mv;
                } catch (Throwable e) {
                        ModelAndView mv = new ModelAndView();
                        mv.setViewName("redirect:/error");
                        mv.addObject("error", "Unexpected error");
                        mv.addObject("message", "Failed to delete an employee: id = "+id);
                        logger.info("Another exception deleting employee"+e.getMessage()+
                                " exception name is: "+ e.getClass());
                        return mv;
                }
                return new ModelAndView("redirect:/admin/employees");
        }

        /*@ExceptionHandler(PersistenceException.class)
        public String handleUnknownIdentifierException(final PersistenceException e,
                                                       final HttpServletRequest request,
                                                       final HttpServletResponse response) {
                logger.info("e: "+e.getMessage());
                logger.info("request: "+request);
                logger.info("response: "+response);
                response.setStatus(403);
                request.setAttribute("error", "Unable to perform the request!");
                request.setAttribute("message", e.getMessage());

                return "redirect:/error";
        }*/

        // Redirect to error page
        @GetMapping("/error")
        public String showErrorPage() {
                return "th/error";
        }

        // Redirect to error page
        @GetMapping("/error/error")
        public ModelAndView showErrorPage2(RedirectAttributes ra) {
                ModelAndView mv = new ModelAndView();
                mv.setViewName("redirect:/error");
                /*mv.addObject("error", "Constraint violation exception deleting employee!");
                mv.addObject("message", "Failed to delete an employee");*/
                ra.addFlashAttribute("error", "Constraint violation exception deleting employee!");
                ra.addFlashAttribute("message", "Failed to delete an employee");
                logger.info("error/error invoked...");
                return mv;
        }

}
