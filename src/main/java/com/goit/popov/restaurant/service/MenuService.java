package com.goit.popov.restaurant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.goit.popov.restaurant.dao.entity.MenuDAO;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Menu;
import com.goit.popov.restaurant.service.dataTables.DataTablesListToJSONConvertible;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


/**
 * Created by Andrey on 08.11.2016.
 */
public class MenuService implements DataTablesListToJSONConvertible<Menu> {

        private MenuDAO menuDAO;

        public void setMenuDAO(MenuDAO menuDAO) {
                this.menuDAO = menuDAO;
        }

        @Transactional
        public List<Menu> getAll() {
                return menuDAO.getAll();
        }

        public Menu getById(int id) {
                return menuDAO.getById(id);
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
