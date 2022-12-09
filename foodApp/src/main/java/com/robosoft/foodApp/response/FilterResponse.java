package com.robosoft.foodApp.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterResponse {

    private String dishType;
    private int count;
    private List<RestaurantResponse> restaurants;
}
