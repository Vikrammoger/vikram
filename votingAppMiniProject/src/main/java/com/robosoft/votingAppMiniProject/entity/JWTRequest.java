package com.robosoft.votingAppMiniProject.entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JWTRequest
{
    private String mobileNumber;
    private String password;
}
