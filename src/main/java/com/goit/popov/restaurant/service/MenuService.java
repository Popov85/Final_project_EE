package com.goit.popov.restaurant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.goit.popov.restaurant.dao.MenuDAO;
import com.goit.popov.restaurant.model.Menu;
import com.goit.popov.restaurant.service.dataTables.DataTablesListToJSONConvertible;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * Created by Andrey on 08.11.2016.
 */
public class MenuService implements DataTablesListToJSONConvertible<Menu> {

        @Autowired
        private MenuDAO menuDAO;

        public List<Menu> getAll() {
                return menuDAO.getAll();
        }

        public Menu getById(int id) {
                return menuDAO.getById(id);
        }

        public int insert(Menu menu) {
                return menuDAO.insert(menu);
        }

        public void updateMenuWithoutDishes(Menu menu) {
                Menu updatedMenu = getById(menu.getId());
                updatedMenu.setName(menu.getName());
                menuDAO.update(updatedMenu);
        }

        public void updateMenusDishes(Menu menu) {
                Menu updatedMenu = getById(menu.getId());
                updatedMenu.setDishes(menu.getDishes());
                menuDAO.update(updatedMenu);
        }

        public void deleteById(int id) {
                menuDAO.delete(getById(id));
        }

        @Override
        public ArrayNode toJSON(List<Menu> menus) {
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode ana = mapper.createArrayNode();
                for (Menu menu : menus) {
                        ObjectNode a = mapper.createObjectNode();
                        a.put("id", menu.getId());
                        a.put("name", menu.getName());
                        a.put("price", menu.getPrice());
                        a.put("dishes", menu.calcDishes());
                        ana.add(a);
                }
                return ana;
        }
}
