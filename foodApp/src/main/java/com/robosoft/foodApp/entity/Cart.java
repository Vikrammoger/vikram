package com.robosoft.foodApp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private int cartId;
    private int userId;
    private int restaurantId;
    private double totalPrice;
}
