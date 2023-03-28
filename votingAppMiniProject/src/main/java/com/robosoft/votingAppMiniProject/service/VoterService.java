package com.robosoft.votingAppMiniProject.service;


import com.robosoft.votingAppMiniProject.entity.*;
import com.robosoft.votingAppMiniProject.request.ExistingPartyRegistration;
import com.robosoft.votingAppMiniProject.request.NewPartyRegistration;
import com.robosoft.votingAppMiniProject.response.PartyResultResponse;
import com.robosoft.votingAppMiniProject.response.ResultResponse;
import com.robosoft.votingAppMiniProject.response.VotingPanelResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;


public interface VoterService {
    String userRegister(User user);





//    String userSignIn(String mobileNumber, String password);


    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    void update2FAProperties(String mobileNumber, String tfacode);

    boolean checkCode(String mobileNumber, String code,String password);

    String wardRegister(Ward ward);

    List<Ward> findAllWards();

    List<Ward> findWardByName(String wardName);

    String voterRegister(Voter voter) throws IOException;

    byte[] getMyProfilePhoto(String voterId);

    String getImageUrl(String voterId);

    Voter viewProfile(String voterId);

    String updateVoterInformation(String voterId, String mobileNumber, String address);

    String createElection(Election election);

    List<Election> findElections();

    List<Election> findElectionByName( String name);

    String updateElectionSchedule(Election election);

    List<Election> findUpcomingElections();

    String addParties(Party party);

    //    ***************************** NOMINATION ****************
    String newPartyRegistration(NewPartyRegistration newPartyRegistration);

    byte[] getPartyLogo(String partyName);



    String getLogoUrl(String partyName);

    List<Party> getParties();

    String existingPartyRegistration(ExistingPartyRegistration existingPartyRegistration);



    List<Candidate> findAllCandidates();

    List<Candidate> findCandidateByName(String name);

    List<PartyRegistration> findAllCandidatesInParty();

    //    ******************************************Voting****************************
    VotingPanelResponse getVotingPanel(String electionName);

    String CastVote(Vote vote);

    String announceResultDate(String electionName, Timestamp result_date);

    //    ******************************RESULT*******************************
    List<Election> getAllResult();

    //    ******************************RESULT*******************************
    ResultResponse getResult(String electionName);


}
