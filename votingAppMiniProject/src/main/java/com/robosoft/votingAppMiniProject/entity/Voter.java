package com.robosoft.votingAppMiniProject.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class Voter {
    private String voterName;
    private String gender;
    private Integer age ;
    private Integer wardNo;
    private String voterId;
    private String aadharNumber;
    private String mobileNumber;
    private String address;
    private MultipartFile profileImage;
    private String profileUrl;
}
