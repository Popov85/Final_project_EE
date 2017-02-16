package com.goit.popov.restaurant.controller.converters;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.json.MenuWrapper;
import com.goit.popov.restaurant.service.DishService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andrey on 07.02.2017.
 */
public class MenuWrapperDeserializer extends JsonDeserializer<MenuWrapper> {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(MenuWrapperDeserializer.class);

        @Autowired
        private DishService dishService;

        @Override
        public MenuWrapper deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                JsonNode node = null;
                try {
                        node = p.getCodec().readTree(p);
                } catch (IOException e) {
                        LOGGER.info("Error while obtaining the node tree: "+e.getMessage());
                }
                MenuWrapper mw = new MenuWrapper();
                try {
                        ArrayNode d = ((ArrayNode) node.get("dishes"));
                        Set<Dish> dishes = convertJSONToSet(d);
                        mw.setDishes(dishes);
                } catch (Exception e) {
                        LOGGER.error("Error: "+e.getMessage());
                }
                return mw;
        }

        private Set<Dish> convertJSONToSet(ArrayNode d) {
                Set<Dish> dishes = new HashSet<>();
                for (JsonNode ingredient : d) {
                        Long dishId = Long.parseLong(ingredient.get("dishId").asText());
                        Dish dish = dishService.getById(dishId);
                        dishes.add(dish);
                }
                return dishes;
        }
}
