package com.goit.popov.restaurant.controller;

import com.goit.popov.restaurant.model.Menu;
import com.goit.popov.restaurant.service.MenuServiceImpl;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

/**
 * Developed for the sake of try!
 * Created by Andrey on 08.11.2016.
 */
@RestController
public class RESTController {

        @Autowired
        MenuServiceImpl menuService;

        @RequestMapping("/menus")
        public String getJSONMenus() throws IOException {
                List<Menu> menus = menuService.getAll();
                StringBuilder json = new StringBuilder();
                ObjectMapper mapper = new ObjectMapper();
                for (Menu menu : menus) {
                        json.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(menu));
                }
                System.out.println(json);
                String formattedJson = json.toString().replace(" ", "&nbsp;");
                formattedJson = formattedJson.toString().replace("\n", "<br>");
                return formattedJson;
        }
}
