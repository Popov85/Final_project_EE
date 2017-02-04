package com.goit.popov.restaurant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.goit.popov.restaurant.dao.entity.DishDAO;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 02.12.2016.
 */
public class DishServiceImpl extends DishService {

        @Autowired
        private DishDAO dishDAO;

        @Override
        public int insert(Dish dish) {
                return dishDAO.insert(dish);
        }

        @Override
        public void update(Dish dish) {
                dishDAO.update(dish);
        }

        @Override
        public List<Dish> getAll() {
                return dishDAO.getAll();
        }

        @Override
        public Dish getById(int id) {
                return dishDAO.getById(id);
        }

        @Override
        public void delete(Dish dish) {
                dishDAO.delete(dish);
        }

        @Override
        public void deleteById(int id) {
                dishDAO.deleteById(id);
        }

        @Override
        public long count() {
                return dishDAO.count();
        }

        @Override
        public List<Dish> getAllItems(DataTablesInputExtendedDTO dt) {
                return dishDAO.getAllItems(dt);
        }

        @Override
        public void updateDishWithoutIngredients(Dish dish) {
                Dish updatedDish = getById(dish.getId());
                updatedDish.setName(dish.getName());
                updatedDish.setCategory(dish.getCategory());
                updatedDish.setWeight(dish.getWeight());
                updatedDish.setPrice(dish.getPrice());
                update(updatedDish);
        }

        @Override
        public void updateDishsIngredients(Dish dish) {
                Dish updatedDish = getById(dish.getId());
                updatedDish.setIngredients(dish.getIngredients());
                update(updatedDish);
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
                        a.put("unit", ingredient.getKey().getUnit().getName());
                        ana.add(a);
                }
                return ana;
        }


}
