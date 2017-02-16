package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Menu;
import com.goit.popov.restaurant.model.json.MenuWrapper;
import com.goit.popov.restaurant.service.MenuService;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOCollectionWrapper;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOListWrapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Set;

/**
 * Created by Andrey on 1/2/2017.
 */
@Controller
public class MenuController {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MenuController.class);

        @Autowired
        private MenuService menuService;

        @GetMapping("/all/show_menus")
        public String showMenus() {
                return "th/all/menus";
        }

        @GetMapping("/admin/menus")
        public String showMenusForAdmin() {
                return "th/manager/menus";
        }

        @GetMapping("/admin/new_menu")
        public ModelAndView showNewMenuForm() {
                Menu menu = new Menu();
                return new ModelAndView("th/manager/new_menu", "menu", menu);
        }

        @GetMapping(value = "/admin/edit_menu")
        public String showMenuEditForm(@RequestParam("menuId") Long menuId, ModelMap map, HttpSession session) {
                Menu menu = menuService.getById(menuId);
                map.addAttribute("menu", menu);
                //if (menu.getDishes()!=null)
                        session.setAttribute("dishes", menu.getDishes());
                return "th/manager/edit_menu";
        }

        @GetMapping(value = "/admin/edit_menus_dishes")
        public String showMenusDishesEditForm(@RequestParam("menuId") int menuId) {
                return "th/manager/edit_menus_dishes";
        }

        @PostMapping("/all/get_menus")
        @ResponseBody
        public DataTablesOutputDTOCollectionWrapper getMenus() {
                DataTablesOutputDTOCollectionWrapper data = new DataTablesOutputDTOCollectionWrapper();
                data.setData(menuService.toJSON(menuService.getAll()));
                return data;
        }

        @GetMapping("/all/show_dishes")
        public ModelAndView getDishes(@RequestParam Long id, ModelAndView modelAndView) {
                modelAndView.addObject("id", menuService.getById(id).getId());
                modelAndView.addObject("menu", menuService.getById(id).getName());
                modelAndView.setViewName("th/all/menus_dishes");
                return modelAndView;
        }

        @PostMapping("/all/get_menus_dishes")
        @ResponseBody
        public DataTablesOutputDTOListWrapper<Dish> getMenusDishes(@RequestParam Long id) {
                DataTablesOutputDTOListWrapper data = new DataTablesOutputDTOListWrapper<>();
                Set<Dish> dishes = menuService.getById(id).getDishes();
                data.setData(dishes);
                return data;
        }

        @PostMapping(value = "/admin/save_menu")
        public String saveMenu(@Valid @ModelAttribute("menu") Menu menu, BindingResult result, RedirectAttributes ra) {
                if (result.hasErrors()) {
                        LOGGER.info("Errors number while saving a new menu: " +
                                result.getFieldErrorCount());
                        return "th/manager/new_menu";
                }
                try {
                        menuService.insert(menu);
                } catch (Exception e) {
                        ra.addFlashAttribute("status", HttpStatus.FORBIDDEN);
                        ra.addFlashAttribute("error", "Failed to insert a new menu");
                        ra.addFlashAttribute("message", "Error: failed to insert a dish #" + menu);
                        return "redirect:/error";
                }
                return "redirect:/admin/menus";
        }

        @PostMapping(value = "/admin/update_menu")
        public String updateMenu(@Valid @ModelAttribute("menu") Menu menu, BindingResult result, RedirectAttributes ra, HttpSession session) {
                if (result.hasErrors()) {
                        LOGGER.info("Errors number while updating menu: " +
                                result.getFieldErrorCount());
                        return "th/manager/edit_menu";
                }
                try {
                        Set<Dish> dishes = (Set<Dish>) session.getAttribute("dishes");
                        menu.setDishes(dishes);
                        menuService.update(menu);
                } catch (Exception e) {
                        ra.addFlashAttribute("status", HttpStatus.FORBIDDEN);
                        ra.addFlashAttribute("error", "Forbidden action!");
                        ra.addFlashAttribute("message", "Error: failed to update the menu #" + menu);
                        return "redirect:/error";
                }
                return "redirect:/admin/menus";
        }

        @PostMapping(value = "/admin/update_menus_dishes")
        public ResponseEntity updateMenusDishes(@RequestBody MenuWrapper menuWrapper, @RequestParam("menuId") Long menuId) {
                try {
                        menuService.updateMenusDishes(menuId, menuWrapper.getDishes());
                } catch (Exception e) {
                        LOGGER.error("Error: failure updating Menu!");
                        return new ResponseEntity("Failure updating Menu!",
                                HttpStatus.FORBIDDEN);
                }
                return new ResponseEntity("{\"Result\": \"Success\"}", HttpStatus.OK);
        }

        @GetMapping("/admin/delete_menu")
        public String delete(@RequestParam Long menuId, RedirectAttributes ra){
                try {
                        menuService.deleteById(menuId);
                } catch (PersistenceException e) {
                        ra.addFlashAttribute("status", HttpStatus.FORBIDDEN);
                        ra.addFlashAttribute("error", "Constraint violation exception deleting menu!");
                        ra.addFlashAttribute("message", "Constraint violation error!");
                        LOGGER.error("Constraint violation exception deleting employee: "+e.getMessage()+
                                "/ exception name is: "+ e.getClass());
                        return "redirect:/error";
                } catch (Throwable e) {
                        ra.addFlashAttribute("status", HttpStatus.FORBIDDEN);
                        ra.addFlashAttribute("error", e.getMessage());
                        ra.addFlashAttribute("message", "Cannot deleteById menu for some reason!");
                        LOGGER.error("Unexpected exception deleting menu: "+e.getMessage()+
                                "/ exception name is: "+ e.getClass());
                        return "redirect:/error";
                }
                return "redirect:/admin/menus";
        }
}
