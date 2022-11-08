package com.robosoft.voterApp.service;

import com.robosoft.voterApp.model.*;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminServiceImpl implements AdminService {
    @Override
    public String adminRegister(Admin admin) {
        return null;
    }

    @Override
    public int adminSignIn(Long mobNumber, String password) {
        return 0;
    }

    @Override
    public String addCandidate(int sId, Candidate candidate) {
        return null;
    }

    @Override
    public List<Candidate> findAllCandidates(int sId) {
        return null;
    }

    @Override
    public List<Candidate> findCandidateById(int sId, int cId) {
        return null;
    }

    @Override
    public String deleteCandidate(int sId, int cId) {
        return null;
    }

    @Override
    public String addParties(int sId, Parties parties) {
        return null;
    }

    @Override
    public List<Parties> findAllParties(int sId) {
        return null;
    }

    @Override
    public List<Parties> findPartyByName(int sId, String name) {
        return null;
    }

    @Override
    public String deleteParty(int sId, String name) {
        return null;
    }

    @Override
    public String addElections(int sId, Election election) {
        return null;
    }

    @Override
    public List<Parties> findAllElections(int sId) {
        return null;
    }

    @Override
    public List<Parties> findElectionByName(int sId, String name) {
        return null;
    }

    @Override
    public String updateElectionSchedule(int sId, int electionId) {
        return null;
    }

    @Override
    public String deleteElection(int sId, String name) {
        return null;
    }

    @Override
    public List<User> findAllVoters(int sId) {
        return null;
    }

}
