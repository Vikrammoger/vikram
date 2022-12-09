package com.robosoft.foodApp.exception;

public class DishNotPresentException extends RuntimeException{
    public DishNotPresentException(String message) {
        super(message);
    }
}
