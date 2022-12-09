package com.robosoft.votingAppMiniProject.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@ToString
public class Election {

    private String electionName;
    private String instruction;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp result_date;

    public String getElectionName() {
        return electionName;
    }

    public void setElectionName(String electionName) {
        this.electionName = electionName;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = Timestamp.valueOf(startDate);
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = Timestamp.valueOf(endDate);
    }

    public Timestamp getResult_date() {
        return result_date;
    }
//format  yyyy-[m]m-[d]d hh:mm:ss[.f...]
    public void setResult_date(String result_date) {
        this.result_date = Timestamp.valueOf(result_date);
    }
}
