package com.robosoft.votingAppMiniProject.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)


public class PartyRegistration{
    private String voterId;
    private String electionName;
    private String partyName;
    private int voteCount;
    private String candidate_name;
    private int wardNumber;
}
