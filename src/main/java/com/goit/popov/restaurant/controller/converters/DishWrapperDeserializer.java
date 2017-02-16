package com.goit.popov.restaurant.controller.converters;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.json.DishWrapper;
import com.goit.popov.restaurant.service.IngredientService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrey on 16.02.2017.
 */
public class DishWrapperDeserializer extends JsonDeserializer<DishWrapper> {

        private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(DishWrapperDeserializer.class);

        @Autowired
        private IngredientService ingredientService;

        @Override
        public DishWrapper deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                JsonNode node = null;
                try {
                        node = p.getCodec().readTree(p);
                } catch (IOException e) {
                        LOGGER.info("Error while obtaining the node tree: "+e.getMessage());
                }
                ArrayNode ingredients = ((ArrayNode) node.get("ingredients"));
                ObjectMapper mapper = new ObjectMapper();
                Map<Ingredient, Double> ingredientsInMap = new HashMap<>();
                try {
                        for (JsonNode jsonNode : ingredients) {
                                Ingredient ingredient = ingredientService.getById(Long.parseLong(jsonNode.get("ingId").asText()));
                                Double quantity = (Double) jsonNode.get("quantity").doubleValue();
                                if (quantity <= 0) throw new RuntimeException("Quantity cannot be negative!");
                                ingredientsInMap.put(ingredient, quantity);
                        }
                } catch (Exception e) {
                        LOGGER.error("Error: "+e.getMessage());
                }
                DishWrapper dw = new DishWrapper();
                dw.setIngredients(ingredientsInMap);
                return dw;
        }
}
