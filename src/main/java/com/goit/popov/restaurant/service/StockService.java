package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.StoreHouseDAO;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.StoreHouse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 18.01.2017.
 */
public class StockService {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(StockService.class);

        @Autowired
        private StoreHouseDAO storeHouseDAO;

        public void decreaseIngredient(Ingredient ingredient, Double quantityRequired) {
                StoreHouse storeHouse = storeHouseDAO.getByIngredient(ingredient);
                Double actualQuantity = storeHouse.getQuantity();
                Double quantityLeft = actualQuantity - quantityRequired;
                storeHouse.setQuantity(quantityLeft);
                storeHouseDAO.update(storeHouse);
                logger.info("Decreased Ingredient: "+ingredient.getName()+
                        "/ quantity left: "+quantityLeft);
        }

        public void increaseIngredient(Ingredient ingredient, Double quantityRequired) {
                StoreHouse storeHouse = storeHouseDAO.getByIngredient(ingredient);
                Double actualQuantity = storeHouse.getQuantity();
                Double quantityLeft = actualQuantity + quantityRequired;
                storeHouse.setQuantity(quantityLeft);
                storeHouseDAO.update(storeHouse);
                logger.info("Increased Ingredient: "+ingredient.getName()+
                        "/ quantity left: "+quantityLeft);
        }

        public void decreaseIngredients(Map<Ingredient, Double> ingredientsRequired) {
                for (Map.Entry<Ingredient, Double> ingredient : ingredientsRequired.entrySet()) {
                       decreaseIngredient(ingredient.getKey(), ingredient.getValue());
                }
        }

        public void increaseIngredients(Map<Ingredient, Double> ingredientsReturned) {
                for (Map.Entry<Ingredient, Double> ingredient : ingredientsReturned.entrySet()) {
                        increaseIngredient(ingredient.getKey(), ingredient.getValue());
                }
        }

        public Double getQuantityByIngredient(Ingredient ingredient) {
              return storeHouseDAO.getQuantityByIngredient(ingredient);
        }

        public List<StoreHouse> getAll() {
                return storeHouseDAO.getAll();
        }

        public Map<Ingredient, Double> convertStockToMap(List<StoreHouse> storeHouse) {
                Map<Ingredient, Double> stock = new HashMap<>();
                for (StoreHouse house : storeHouse) {
                        stock.put(house.getIngredient(), house.getQuantity());
                }
                return stock;
        }

        public void toStringStock(Map<Ingredient, Double> stock) {
                for (Map.Entry<Ingredient, Double> ingredient : stock.entrySet()) {
                        logger.info("Ingredient: "+ingredient.getKey().getName()+" : "+ingredient.getValue());
                }
        }
}
