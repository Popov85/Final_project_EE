package com.goit.popov.restaurant.controller.converters;

import com.goit.popov.restaurant.model.Position;
import com.goit.popov.restaurant.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Andrey on 11/15/2016.
 */
public class PositionConverter implements Converter<String, Position> {

        @Autowired
        private PositionService positionService;

        public void setPositionService(PositionService positionService) {
                this.positionService = positionService;
        }

        @Override
        public Position convert(String name) {
                return this.positionService.getPositionByName(name);
        }
}
