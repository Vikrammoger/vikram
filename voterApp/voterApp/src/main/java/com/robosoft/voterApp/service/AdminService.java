package com.robosoft.voterApp.service;

import com.robosoft.voterApp.model.*;

import java.util.List;

public interface AdminService {

    String adminRegister(Admin admin);
    int adminSignIn(Long mobNumber,String password);
    String addCandidate(int sId, Candidate candidate);
    List<Candidate> findAllCandidates(int sId);
    List<Candidate> findCandidateById(int sId,int cId);
    String deleteCandidate(int sId,int cId);
    String addParties(int sId,Parties parties);
    List<Parties> findAllParties(int sId);
    List<Parties> findPartyByName(int sId,String name);
    String deleteParty(int sId,String name);
    String addElections(int sId, Election election);

    List<Parties> findAllElections(int sId);
    List<Parties> findElectionByName(int sId,String name);
    String updateElectionSchedule(int sId,int electionId);
    String deleteElection(int sId,String name);
    List<User> findAllVoters(int sId);



}
