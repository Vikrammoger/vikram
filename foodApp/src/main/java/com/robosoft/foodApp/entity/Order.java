package com.robosoft.foodApp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int orderId;
    private int cartId;
    private long cardNo;
    private String address;
    private String status;
}
