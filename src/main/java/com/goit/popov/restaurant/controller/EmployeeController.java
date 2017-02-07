package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.*;
import com.goit.popov.restaurant.service.EmployeeService;
import com.goit.popov.restaurant.service.StaffService;
import com.goit.popov.restaurant.service.PositionService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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
        private static final String DELETION_FAILURE_MESSAGE = "Failed to delete an employee: id =";

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
                positionsList.put("0", "--Select--");
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
                model.put("employees", employeeService.getAll());
                return "th/manager/employees";
        }

        // New form
        @RequestMapping("/admin/new_employee")
        public ModelAndView showEmployeeForm(){
                return new ModelAndView("jsp/manager/new_employee","employee",new Employee());
        }

        // Create
        @RequestMapping(value="/admin/save_employee",method = RequestMethod.POST)
        public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, Model model,
                                   @RequestParam("position") String position, @RequestParam("photo") MultipartFile photo){
                if (result.hasErrors()) {
                        logger.error("# of errors is: "+result.getFieldErrorCount());
                        return "jsp/manager/new_employee";
                }
                StaffService staffService;
                if ((position.equals("Waiter")) || (position.equals("Chef"))
                        || (position.equals("Manager"))) {
                        staffService = (StaffService) applicationContext.getBean(position);
                } else {
                        staffService = (StaffService) applicationContext.getBean("Employee");
                }
                try {
                        staffService.insert(employee);
                } catch (DataIntegrityViolationException e) {
                        model.addAttribute("constraintViolationError", NON_UNIQUE_CONSTRAINT_MESSAGE);
                        logger.error("Constraint violation exception inserting employee: "+e.getMessage()+
                                " exception name is: "+ e.getClass());
                        return "jsp/manager/new_employee";
                } catch (Throwable e) {
                        model.addAttribute("unexpectedError", e.getMessage());
                        logger.error("Another error inserting employee: "+e.getMessage()+
                                "/ exception name is: "+ e.getClass());
                        return "jsp/manager/new_employee";
                }
                logger.info("Successfully added employee: "+employee);
                return "redirect:/admin/employees";
        }

        // Read (update form)
        @RequestMapping(value = "/admin/edit_employee/{id}", method = RequestMethod.GET)
        public String showEmployeeEditForm(@PathVariable("id") int id, ModelMap map, HttpSession session){
                Employee employee = employeeService.getById(id);
                logger.info("Updating class: "+employee.getClass()+" employee: "+employee);
                map.addAttribute("employee", employee);
                map.addAttribute("positions", populatePositions());
                map.addAttribute("position", employee.getPosition().getName());
                session.setAttribute("position",employee.getPosition().getName());
                session.setAttribute("file",employee.getPhoto());
                return "jsp/manager/update_employee";
        }

        // Update
        @RequestMapping(value="/admin/update_employee", method = RequestMethod.POST)
        public String updateEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result,
                                     @RequestParam("position") String newPosition, HttpSession session, Model model,
                                     @RequestParam("photo") MultipartFile photo){
                logger.info("Updating controller: "+employee.getClass());
                if (result.hasErrors()) {
                        logger.error("# of errors is: "+result.getFieldErrorCount());
                        return "jsp/manager/update_employee";
                }
                String previousPosition = (String) session.getAttribute("position");
                StaffService staffService;
                if ((newPosition.equals("Waiter")) || (newPosition.equals("Chef"))
                        || (newPosition.equals("Manager"))) {
                        staffService = (StaffService) applicationContext.getBean(newPosition);
                } else {
                        staffService = (StaffService) applicationContext.getBean("Employee");
                }
                if (photo.isEmpty()) employee.setPhoto((byte[]) session.getAttribute("file"));
                try {
                        if (!previousPosition.equals(newPosition)) {
                                logger.info("Position changed! Previous was: "+previousPosition+
                                                "/ new position is: "+newPosition);
                                staffService.updateThroughDelete(employee);
                        } else {
                                staffService.update(employee);
                        }
                } catch (DataIntegrityViolationException e) {
                        model.addAttribute("constraintViolationError", NON_UNIQUE_CONSTRAINT_MESSAGE);
                        logger.error("Constraint violation exception updating employee: "+e.getMessage()+
                                "/ exception name is: "+ e.getClass());
                        return "jsp/manager/update_employee";
                } catch (PersistenceException e) {
                        model.addAttribute("integrityViolationError", e.getMessage());
                        logger.error("Data integrity exception updating employee: "+e.getMessage()+
                                "/ exception name is: "+ e.getClass());
                        return "jsp/manager/update_employee";
                } catch (Throwable e) {
                        model.addAttribute("unexpectedError", e.getMessage());
                        logger.error("Another error updating employee: "+e.getMessage()+
                                "/ exception name is: "+ e.getClass());
                        return "jsp/manager/update_employee";
                }
                logger.info("Successfully updated employee: "+employee);
                return "redirect:/admin/employees";
        }

        // Delete
        @RequestMapping(value="/admin/delete_employee/{id}",method = RequestMethod.GET)
        public String delete(@PathVariable int id, RedirectAttributes ra){
                try {
                        employeeService.deleteById(id);
                } catch (PersistenceException e) {
                        ra.addFlashAttribute("status", HttpStatus.FORBIDDEN);
                        ra.addFlashAttribute("error", "Constraint violation exception deleting employee!");
                        ra.addFlashAttribute("message", DELETION_FAILURE_MESSAGE +id);
                        logger.error("Constraint violation exception deleting employee: "+e.getMessage()+
                                "/ exception name is: "+ e.getClass());
                        return "redirect:/error";
                } catch (Throwable e) {
                        ra.addFlashAttribute("status", HttpStatus.FORBIDDEN);
                        ra.addFlashAttribute("error", e.getMessage());
                        ra.addFlashAttribute("message", DELETION_FAILURE_MESSAGE +id);
                        logger.error("Unexpected exception deleting employee: "+e.getMessage()+
                                "/ exception name is: "+ e.getClass());
                        return "redirect:/error";
                }
                return "redirect:/admin/employees";
        }
}
