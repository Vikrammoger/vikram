package com.robosoft.foodApp.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class Card {
    private  int userId;
    private long cardNo;
}
