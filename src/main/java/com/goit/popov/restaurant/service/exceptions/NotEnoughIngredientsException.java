package com.goit.popov.restaurant.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Andrey on 25.01.2017.
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason="Not enough ingredients to fulfill the request")
public class NotEnoughIngredientsException extends Exception{

        public NotEnoughIngredientsException() {
                super();
        }

        public NotEnoughIngredientsException(String message) {
                super(message);
        }
}
