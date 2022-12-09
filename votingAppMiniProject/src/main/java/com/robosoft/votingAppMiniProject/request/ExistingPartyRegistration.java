package com.robosoft.votingAppMiniProject.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ExistingPartyRegistration {
    private String partyName;
    private String candidateName;
    private String candidateGender;
    private Integer candidateAge;
    private Integer wardNo;
    private String voterId;
    private String electionName;
}
