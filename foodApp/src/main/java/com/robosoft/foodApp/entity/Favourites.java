package com.robosoft.foodApp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Favourites {
    private  int userId;
    private int restaurantId;
}
