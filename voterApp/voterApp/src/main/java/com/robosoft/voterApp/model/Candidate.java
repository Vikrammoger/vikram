package com.robosoft.voterApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {
    private int candidateId;
    private String voterId;
    private String name;
    private Long voteCount;
}
