package com.robosoft.foodApp.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filter {
    private String dishType;
    private boolean openNow;
    private boolean creditCard;
    private boolean freeDelivery;
    private double price;
    private String sortBy;
}
