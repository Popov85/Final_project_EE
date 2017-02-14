package com.goit.popov.restaurant.controller.converters;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Menu;
import com.goit.popov.restaurant.service.DishService;
import com.goit.popov.restaurant.service.MenuService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andrey on 07.02.2017.
 */
public class MenuDeserializer extends JsonDeserializer<Menu> {

        private static Logger logger = (Logger) LoggerFactory.getLogger(MenuDeserializer.class);

        @Autowired
        private MenuService menuService;

        @Autowired
        private DishService dishService;

        @Override
        public Menu deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                JsonNode node = null;
                try {
                        node = p.getCodec().readTree(p);
                } catch (IOException e) {
                        logger.info("Error while obtaining the node tree: "+e.getMessage());
                }
                Menu menu = null;
                try {
                        Long menuId = (Long) ((IntNode) node.get("menuId")).numberValue();
                        menu = menuService.getById(menuId);
                        ArrayNode ingredients = ((ArrayNode) node.get("dishes"));
                        Set<Dish> dishes = convertJSONToSet(ingredients);
                        menu.setDishes(dishes);
                } catch (Exception e) {
                        logger.error("Error: "+e.getMessage());
                }
                return menu;
        }

        private Set<Dish> convertJSONToSet(ArrayNode ingredients) {
                Set<Dish> dishes = new HashSet<>();
                for (JsonNode ingredient : ingredients) {
                        Long dishId = (Long) ingredient.get("dishId").longValue();
                        Dish dish = dishService.getById(dishId);
                        dishes.add(dish);
                }
                return dishes;
        }
}
