package com.goit.popov.restaurant.controller.converters;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.goit.popov.restaurant.model.Dish;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.service.DishService;
import com.goit.popov.restaurant.service.IngredientService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrey on 03.02.2017.
 */
public class DishDeserializer extends JsonDeserializer<Dish> {

        private static Logger logger = (Logger) LoggerFactory.getLogger(DishDeserializer.class);

        @Autowired
        private DishService dishService;

        @Autowired
        private IngredientService ingredientService;

        @Override
        public Dish deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                JsonNode node = null;
                try {
                        node = p.getCodec().readTree(p);
                } catch (IOException e) {
                        logger.info("Error while obtaining the node tree: "+e.getMessage());
                }
                Dish dish = null;
                try {
                        Long dishId = (Long) ((IntNode) node.get("dishId")).numberValue();
                        dish = dishService.getById(dishId);
                        ArrayNode ingredients = ((ArrayNode) node.get("ingredients"));
                        Map<Ingredient, Double> ingredientsInMap = convertJSONToMap(ingredients);
                        dish.setIngredients(ingredientsInMap);
                } catch (Exception e) {
                        logger.error("Error: "+e.getMessage());
                }

                return dish;
        }

        private Map<Ingredient,Double> convertJSONToMap(ArrayNode ingredients) {
                ObjectMapper mapper = new ObjectMapper();
                Map<Ingredient, Double> ingredientsInMap = new HashMap<>();
                try {
                        for (JsonNode jsonNode : ingredients) {
                                Ingredient ingredient = ingredientService.getById((Long) jsonNode.get("ingId").numberValue()); ;
                                Double quantity = (Double) jsonNode.get("quantity").doubleValue();
                                if (quantity <= 0) throw new RuntimeException("Quantity cannot be negative!");
                                ingredientsInMap.put(ingredient, quantity);
                        }
                } catch (Exception e) {
                        logger.error("Error: "+e.getMessage());
                }
                return ingredientsInMap;
        }
}
