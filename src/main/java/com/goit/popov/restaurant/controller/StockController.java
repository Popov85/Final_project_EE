package com.goit.popov.restaurant.controller;

import ch.qos.logback.classic.Logger;
import com.goit.popov.restaurant.model.StoreHouse;
import com.goit.popov.restaurant.service.StockService;
import com.goit.popov.restaurant.service.dataTables.DataTablesInputExtendedDTO;
import com.goit.popov.restaurant.service.dataTables.DataTablesOutputDTOUniversal;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Andrey on 31.01.2017.
 */
@Controller
public class StockController {

        static Logger logger = (Logger) LoggerFactory.getLogger(StockController.class);

        @Autowired
        private StockService stockService;

        @GetMapping(value = "/adm/stock_state")
        public String showStock() {
                return "th/manager/stock";
        }

        @PostMapping(value = "/adm/get_stock_state")
        @ResponseBody
        public DataTablesOutputDTOUniversal<StoreHouse> getStockState(DataTablesInputExtendedDTO input) {
                logger.info("input: "+input);
                DataTablesOutputDTOUniversal<StoreHouse> data = stockService.getAll(input);
                logger.info("output: "+data);
                return data;
        }

        @GetMapping(value = "/get_stock")
        @ResponseBody
        public List<StoreHouse> getStock() {
                return stockService.getAll();
        }
}
