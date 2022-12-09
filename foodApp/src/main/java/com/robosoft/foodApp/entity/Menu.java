package com.robosoft.foodApp.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu {
    private int restaurantId;
    private  int dishId;
    private String name;
    private double price;
    private MultipartFile image;
    private String imageUrl;
    private String dishType;
}
