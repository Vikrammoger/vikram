package com.robosoft.votingAppMiniProject.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Candidate {
    private String candidateName;
    private String candidateGender;
    private int candidateAge;
    private int wardNo;
    private String candidateVoterId;

}
