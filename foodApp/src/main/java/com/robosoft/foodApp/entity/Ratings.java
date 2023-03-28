package com.robosoft.foodApp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ratings {
    private  int userId;
    private int restaurantId;
    private double rating;
}
