package com.robosoft.voterApp.service;

import com.robosoft.voterApp.model.Admin;
import com.robosoft.voterApp.model.Candidate;
import com.robosoft.voterApp.model.Election;
import com.robosoft.voterApp.model.Voter;

import java.util.List;

public interface UserService {
    String userRegister(Admin admin);
    int userSignIn(Long mobNumber,String password);
    List<Election> findUpcomingElections(int sId);

    String castVote(String partyName);

    Election electionResult(String electionName );

    String forgotPassword();

    Voter viewProfile();


    Candidate winner();

    
}
