package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.Ingredient;
import com.goit.popov.restaurant.model.StoreHouse;
import com.goit.popov.restaurant.service.IngredientService;
import com.goit.popov.restaurant.service.StockService;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOUniversal;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Andrey on 31.01.2017.
 */
@Controller
public class StockController {

        static Logger logger = (Logger) LoggerFactory.getLogger(StockController.class);

        @Autowired
        private StockService stockService;

        @Autowired
        private IngredientService ingredientService;

        @GetMapping(value = "/admin/stock_state")
        public String showStock() {
                return "th/manager/stock";
        }

        @PostMapping(value = "/admin/get_stock_state")
        @ResponseBody
        public DataTablesOutputDTOUniversal<StoreHouse> getStockState(DataTablesInputExtendedDTO input) {
                DataTablesOutputDTOUniversal<StoreHouse> data = stockService.getAll(input);
                return data;
        }

        @GetMapping(value = "/admin/update_stock")
        public String updateStock(@RequestParam int ingId, @RequestParam double quantity) {
                Ingredient ingredient = ingredientService.getById(ingId);
                stockService.increaseIngredient(ingredient, quantity);
                logger.info("Increased: ingId= "+ingId+" quantity= "+quantity);
                return "redirect:/admin/stock_state";
        }
}
