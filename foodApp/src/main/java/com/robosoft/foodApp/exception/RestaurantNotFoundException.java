package com.robosoft.foodApp.exception;

public class RestaurantNotFoundException extends  RuntimeException{
    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
