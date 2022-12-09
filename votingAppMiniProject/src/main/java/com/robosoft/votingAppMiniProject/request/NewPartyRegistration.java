package com.robosoft.votingAppMiniProject.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class NewPartyRegistration {
    private String partyName;
    private MultipartFile partyLogo;
    private String logoUrl;
    private String candidateName;
    private String candidateGender;
    private Integer candidateAge;
    private Integer wardNo;
    private String voterId;
    private String electionName;
}
