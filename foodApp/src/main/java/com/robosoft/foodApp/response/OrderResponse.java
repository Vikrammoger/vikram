package com.robosoft.foodApp.response;

import lombok.Data;

@Data
public class OrderResponse {
    private int orderId;
    private int cartId;
    private int cardNo;
    private String address;
    private String status;
    private int restaurantId;
    private double totalPrice;

}
