package com.robosoft.votingAppMiniProject.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Vote {
    private String voterId;
    private String partyName;
    private String electionName;
    private int wardNo;
}
