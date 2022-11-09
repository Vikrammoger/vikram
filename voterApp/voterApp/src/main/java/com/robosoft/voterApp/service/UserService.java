package com.robosoft.voterApp.service;

import com.robosoft.voterApp.model.*;

import java.util.List;

public interface UserService {
    String userRegister(User user);
    String userSignIn(Long mobNumber,String password);
    List<Election> findUpcomingElections();
    String castVote(String partyName);
    Election electionResult(String electionName);
    String forgotPassword();
    Voter viewProfile();

    Candidate winner();

    
}
