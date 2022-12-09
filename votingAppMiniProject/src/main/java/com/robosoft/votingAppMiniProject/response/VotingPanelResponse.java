package com.robosoft.votingAppMiniProject.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class VotingPanelResponse {

    private String electionName;
    private Timestamp electionDate;
    private List<VotingResponse> parties;

}
