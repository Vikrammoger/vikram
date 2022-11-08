package com.robosoft.voterApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parties {
    private String partyName;
    private MultipartFile logo;
    private String logoUrl;
    private int candidateId;
    private String gender;
    private int age;
    private int wardNumber;
    private String voterId;


}
