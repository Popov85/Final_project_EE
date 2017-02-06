package com.goit.popov.restaurant.service;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.dao.entity.StoreHouseDAO;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.StoreHouse;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOUniversal;
import com.goit.popov.restaurant.service.dataTables.service.StockServerSideProcessing;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrey on 18.01.2017.
 */
public class StockServiceImpl implements StockService {

        private static final Logger logger = (Logger) LoggerFactory.getLogger(StockServiceImpl.class);

        @Autowired
        private StoreHouseDAO storeHouseDAO;

        @Autowired
        private StockServerSideProcessing stockServerSideProcessing;

        @Override
        public int insert(StoreHouse storeHouse) {
                return storeHouseDAO.insert(storeHouse);
        }

        @Override
        public void update(StoreHouse storeHouse) {
                storeHouseDAO.update(storeHouse);
        }

        @Override
        public List<StoreHouse> getAll() {
                return storeHouseDAO.getAll();
        }

        @Override
        public StoreHouse getById(int id) {
                // TODO check if throe UnsupportedOperationException
                return storeHouseDAO.getById(id);
        }

        @Override
        public void delete(StoreHouse storeHouse) {
                storeHouseDAO.delete(storeHouse);
        }

        @Override
        public List<StoreHouse> getAllRunOut(double threshold) {
                return storeHouseDAO.getAllRunOut(threshold);
        }

        @Override
        public StoreHouse getByIngredient(Ingredient ingredient) {
                return storeHouseDAO.getByIngredient(ingredient);
        }

        @Override
        public Double getQuantityByIngredient(Ingredient ingredient) {
                return storeHouseDAO.getQuantityByIngredient(ingredient);
        }

        @Override
        public long count() {
                return storeHouseDAO.count();
        }

        @Override
        public List<StoreHouse> getAllItems(DataTablesInputExtendedDTO dt) {
                return storeHouseDAO.getAllItems(dt);
        }

        @Override
        public void decreaseIngredient(Ingredient ingredient, Double quantityRequired) {
                StoreHouse storeHouse = getByIngredient(ingredient);
                Double actualQuantity = storeHouse.getQuantity();
                Double quantityLeft = actualQuantity - quantityRequired;
                storeHouse.setQuantity(quantityLeft);
                update(storeHouse);
                logger.info("Decreased Ingredient: "+ingredient.getName()+
                        "/ quantity left: "+quantityLeft);
        }

        @Override
        public void increaseIngredient(Ingredient ingredient, Double quantityReturned) {
                StoreHouse storeHouse = getByIngredient(ingredient);
                Double actualQuantity = storeHouse.getQuantity();
                Double quantityIncreased = actualQuantity + quantityReturned;
                storeHouse.setQuantity(quantityIncreased);
                update(storeHouse);
                logger.info("Increased Ingredient: "+ingredient.getName()+
                        "/ quantity left: "+quantityIncreased);
        }

        @Override
        @Transactional
        public void decreaseIngredients(Map<Ingredient, Double> ingredientsRequired) {
                for (Map.Entry<Ingredient, Double> ingredient : ingredientsRequired.entrySet()) {
                       decreaseIngredient(ingredient.getKey(), ingredient.getValue());
                }
        }

        @Override
        @Transactional
        public void increaseIngredients(Map<Ingredient, Double> ingredientsReturned) {
                for (Map.Entry<Ingredient, Double> ingredient : ingredientsReturned.entrySet()) {
                        increaseIngredient(ingredient.getKey(), ingredient.getValue());
                }
        }

        @Override
        public DataTablesOutputDTOUniversal<StoreHouse> getAll(DataTablesInputExtendedDTO dt) {
                return stockServerSideProcessing.getAll(dt);
        }
}
