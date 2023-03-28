package com.robosoft.votingAppMiniProject.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ResultResponse {
    private String candidateName;
    private String profileUrl;
    private Integer votes;
    private String electionName;
    private int wardNo;
    private List<PartyResultResponse> partyResult;
}
