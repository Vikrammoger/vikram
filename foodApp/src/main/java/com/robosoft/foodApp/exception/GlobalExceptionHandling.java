package com.robosoft.foodApp.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseEntity<String> handleEmptyResultDataAccessException(EmptyResultDataAccessException emptyResultDataAccessException){
        emptyResultDataAccessException.printStackTrace();
        return new ResponseEntity<String>("Empty ResultData AccessException..", HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = DishNotPresentException.class)
    public ResponseEntity<String> handleDishNotPresentException(DishNotPresentException dishNotPresentException){
        dishNotPresentException.printStackTrace();
        return new ResponseEntity<String>(dishNotPresentException.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = RestaurantNotFoundException.class)
    public ResponseEntity<String> handleRestaurantNotFoundException(RestaurantNotFoundException restaurantNotFoundException){
        restaurantNotFoundException.printStackTrace();
        return new ResponseEntity<String>(restaurantNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EmptyCartException.class)
    public ResponseEntity<String> handleEmptyCartException(EmptyCartException emptyCartException){
        emptyCartException.printStackTrace();
        return new ResponseEntity<String>(emptyCartException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
