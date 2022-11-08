package com.robosoft.voterApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voter {
    private String voterId;
    private String name;
    private String gender;
    private int age;
    private int wardNumber;
    private Long aadharNumber;
    private Long mobNumber;
    private String address;
    private MultipartFile profileImage;
    private String profileUrl;

}
