package com.robosoft.voterApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Election {
   private String electionName;
   private Timestamp startDateAndTime;
   private Timestamp endDateAndTime;


}
