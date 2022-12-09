package com.robosoft.foodApp.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponse {
    private String dishType;
    private int restaurantId;
    private String name;
    private String location;
    private  String description;
    private boolean freeDelivery;
    private int ratings;
    private int favourites;
    private double overAllRatings;
    private Time openTime;
    private Time closeTime;
    private String imageUrl;
    private double minCost;
    private boolean creditCard;
}
