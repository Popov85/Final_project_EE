package com.goit.popov.restaurant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.goit.popov.restaurant.dao.entity.DishDAO;
import com.goit.popov.restaurant.dao.entity.StoreHouseDAO;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.Menu;
import com.goit.popov.restaurant.service.dataTables.DataTablesListToJSONConvertible;
import com.goit.popov.restaurant.service.dataTables.DataTablesMapToJSONConvertible;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 02.12.2016.
 */
public class DishService implements DishServiceInterface, DataTablesListToJSONConvertible<Dish>,
        DataTablesMapToJSONConvertible<Ingredient, Double> {

        @Autowired
        private DishDAO dishDAO;

        @Autowired
        private StoreHouseDAO storeHouseDAO;

        public void insert(Dish dish) {
                dishDAO.insert(dish);
        }

        public void update(Dish dish) {
                dishDAO.update(dish);
        }

        public List<Dish> getAll() {
                return dishDAO.getAll();
        }

        public Dish getById(int id) {
                return dishDAO.getById(id);
        }

        public void delete(Dish dish) {
                dishDAO.delete(dish);
        }

        @Deprecated
        @Transactional
        @Override
        public boolean validateIngredients(int id, int number) {
                Map<Ingredient, Double> ingredients = getIngredients(id);
                for (Map.Entry<Ingredient, Double> entry : ingredients.entrySet()) {
                        Ingredient ingredient = entry.getKey();
                        Double quantityRequired = number * entry.getValue();
                        Double quantityInStock = storeHouseDAO.getById(ingredient.getId()).getQuantity();
                        /*For each ingredient of the dish (id) compare how much of the ingredient there is in stock
                         and how much is actually required to cook the dish*/
                        if (quantityInStock < quantityRequired) {
                                return false;
                        }
                }
                return true;
        }

        @Transactional
        @Override
        public Map<Ingredient, Double> getIngredients(int id) {
                Dish dish = getById(id);
                return dish.getIngredients();
        }

        @Override
        public ArrayNode toJSON(List<Dish> dishes) {
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode ana = mapper.createArrayNode();
                for (Dish dish : dishes) {
                        ObjectNode a = mapper.createObjectNode();
                        a.put("id", dish.getId());
                        a.put("name", dish.getName());
                        a.put("category", dish.getCategory());
                        a.put("price", dish.getPrice());
                        a.put("weight", dish.getWeight());
                        List<Menu> menus = dish.getMenus();
                        StringBuilder sb = new StringBuilder();
                        for (Menu menu : menus) {
                              sb.append(menu.getName());
                                sb.append(", ");
                        }
                        String m = sb.toString();
                        m = m.substring(0, m.length() - 2);
                        a.put("menus", m);
                        ana.add(a);
                }
                return ana;
        }

        @Override
        public ArrayNode toJSON(Map<Ingredient, Double> ingredients) {
                ObjectMapper mapper = new ObjectMapper();
                ArrayNode ana = mapper.createArrayNode();
                for (Map.Entry<Ingredient, Double> ingredient : ingredients.entrySet()) {
                        ObjectNode a = mapper.createObjectNode();
                        a.put("id", ingredient.getKey().getId());
                        a.put("name", ingredient.getKey().getName());
                        a.put("quantity", ingredient.getValue());
                        ana.add(a);
                }
                return ana;
        }

}
