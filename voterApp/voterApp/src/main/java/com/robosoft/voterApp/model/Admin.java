package com.robosoft.voterApp.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Admin {

    private int adminId;
    private String adminName;
    private String email;
    private Long mobNumber;
    private String password;
}
