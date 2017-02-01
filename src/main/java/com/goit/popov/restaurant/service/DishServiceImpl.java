package com.goit.popov.restaurant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.goit.popov.restaurant.dao.entity.DishDAO;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.Menu;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOUniversal;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 02.12.2016.
 */
public class DishServiceImpl implements DishService {

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

        @Override
        public long count() {
                return dishDAO.count();
        }

        @Override
        public List<Dish> getAllDishes(DataTablesInputExtendedDTO dt) {
                return dishDAO.getAllDishes(dt);
        }

        @Override
        public DataTablesOutputDTOUniversal<Dish> getAll(DataTablesInputExtendedDTO dt) {
                long recordsTotal = count();
                long recordsFiltered;
                List<Dish> data = getAllDishes(dt);
                if (!dt.getColumnSearch().isEmpty()) {
                        recordsFiltered = data.size();
                } else {
                        recordsFiltered=recordsTotal;
                }
                return new DataTablesOutputDTOUniversal<Dish>()
                        .setDraw(dt.getDraw())
                        .setRecordsTotal(recordsTotal)
                        .setRecordsFiltered(recordsFiltered)
                        .setData(data);
        }
}
