package com.robosoft.foodApp.response;

import com.robosoft.foodApp.entity.Item;
import lombok.Data;

import java.util.List;

@Data
public class CartItems {
    private int restaurantId;
    private double totalPrice;
    private List<Item> items;
}
