package com.robosoft.voterApp.service;

import com.robosoft.voterApp.model.Admin;
import com.robosoft.voterApp.model.Candidate;
import com.robosoft.voterApp.model.Election;
import com.robosoft.voterApp.model.Voter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public String userRegister(Admin admin) {
        return null;
    }

    @Override
    public int userSignIn(Long mobNumber, String password) {
        return 0;
    }

    @Override
    public List<Election> findUpcomingElections(int sId) {
        return null;
    }

    @Override
    public String castVote(String partyName) {
        return null;
    }

    @Override
    public Election electionResult(String electionName) {
        return null;
    }

    @Override
    public String forgotPassword() {
        return null;
    }

    @Override
    public Voter viewProfile() {
        return null;
    }

    @Override
    public Candidate winner() {
        return null;
    }
}
