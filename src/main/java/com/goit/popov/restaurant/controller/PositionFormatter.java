package com.goit.popov.restaurant.controller;

import com.goit.popov.restaurant.model.Position;
import com.goit.popov.restaurant.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by Andrey on 11.11.2016.
 */
public class PositionFormatter implements Formatter<Position> {

        @Autowired
        PositionService positionService;

        public void setPositionService(PositionService positionService) {
                this.positionService = positionService;
        }

        @Override
        public Position parse(String id, Locale locale) throws ParseException {
                System.out.println("id = "+id);
                return positionService.getById(Integer.parseInt(id));
        }

        @Override
        public String print(Position position, Locale locale) {
                System.out.println("position= "+position);
                return position.getName();
        }
}
