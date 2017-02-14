package com.goit.popov.restaurant.controller.converters;

import com.goit.popov.restaurant.model.Unit;
import com.goit.popov.restaurant.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Andrey on 11/28/2016.
 */
public class UnitConverter implements Converter<String, Unit> {

        @Autowired
        private UnitService unitService;

        @Override
        public Unit convert(String unitId) {
                return unitService.getById(Long.valueOf(unitId));
        }
}
