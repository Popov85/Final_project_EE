package com.goit.popov.restaurant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.goit.popov.restaurant.dao.MenuDAO;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Set;

/**
 * Created by Andrey on 08.11.2016.
 */
public class MenuServiceImpl implements MenuService {

        @Autowired
        private MenuDAO menuDAO;

        @Override
        public List<Menu> getAll() {
                return menuDAO.getAll();
        }

        @Override
        public Menu getById(Long id) {
                return menuDAO.getById(id);
        }

        @Override
        public void delete(Menu menu) {
                menuDAO.delete(menu);
        }

        @Override
        public Long insert(Menu menu) {
                return menuDAO.insert(menu);
        }

        @Override
        public void update(Menu menu) {
                menuDAO.update(menu);
        }

        @Override
        public void updateMenusDishes(Long menuId, Set<Dish> dishes) {
                Menu updatedMenu = getById(menuId);
                updatedMenu.setDishes(dishes);
                menuDAO.update(updatedMenu);
        }

        @Override
        public void deleteById(Long id) {
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
