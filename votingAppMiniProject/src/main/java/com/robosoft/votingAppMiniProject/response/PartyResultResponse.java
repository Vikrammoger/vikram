package com.robosoft.votingAppMiniProject.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PartyResultResponse {
    private String partyLogo;
    private String candidateName;
    private String partyName;
    private Integer votes;
}
