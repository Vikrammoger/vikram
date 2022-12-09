package com.robosoft.votingAppMiniProject.service;


import com.robosoft.votingAppMiniProject.entity.*;

import com.robosoft.votingAppMiniProject.request.ExistingPartyRegistration;
import com.robosoft.votingAppMiniProject.request.NewPartyRegistration;
import com.robosoft.votingAppMiniProject.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;



import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class VoterServiceImpl implements VoterService, UserDetailsService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SmsService smsService;



    @Override
    public String userRegister(User user) {
        String query = "insert into user(mobile_number,password,email) values(" + user.getMobileNumber() + ",'" + user.getPassword() + "','"+user.getEmail()+"')";
        jdbcTemplate.update(query);
        return "user added successfully";
    }

//    @Override
//    public String userSignIn(String mobileNumber,String password) {
//        try {
//            String query = "select mobile_number,password from user where mobile_number=" + mobileNumber + " and password='" +password+ "'";
//            jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(KafkaProperties.Admin.class));
//            return "signed in";
//        } catch (Exception e) {
//            return "invalid credentials";
//        }
//
//    }


    @Override
    public UserDetails loadUserByUsername(String mobileNumber) throws UsernameNotFoundException
    {
        try {
            String emailId = jdbcTemplate.queryForObject("select mobile_number from user where mobile_number=?", String.class, new Object[]{mobileNumber});
            String password = jdbcTemplate.queryForObject("select password from user where mobile_number=?", String.class, new Object[]{mobileNumber});
            return new org.springframework.security.core.userdetails.User(emailId, password, new ArrayList<>());
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    @Override
    public void update2FAProperties(String email, String tfacode)
    {
        jdbcTemplate.update("update user set 2fa_code=?, 2fa_expiry_time=? where email=?", new Object[]
                {
                        tfacode,(System.currentTimeMillis()/1000)+60,email
                });
    }
    @Override
    public boolean checkCode(String mobileNumber, String code,String password)
    {
        try {
            boolean store= jdbcTemplate.queryForObject("select count(*) from user where 2fa_code=? and mobile_number=? and 2fa_expiry_time>=?", new Object[]{code, mobileNumber, System.currentTimeMillis() / 1000}, Integer.class) > 0;
            String update_query="update user set password='"+password+"' where mobile_number='"+mobileNumber+"'";
            jdbcTemplate.update(update_query);
            return  store;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }





    @Override
    public String wardRegister(Ward ward) {
        String query = "insert into ward (ward_name) values('" + ward.getWardName() + "')";
        jdbcTemplate.update(query);
        return "ward added successfully";
    }
    @Override
    public List<Ward> findAllWards() {
        String query = "select * from ward";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Ward.class));
    }

    @Override
    public List<Ward> findWardByName(String wardName) {
            String query = "select * from ward where ward_name='" + wardName+"'";
            return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Ward.class));

    }


    @Override
    public String voterRegister(Voter voter) {
        String fileName = StringUtils.cleanPath(voter.getProfileImage().getOriginalFilename());
        String downloadUrl;

        if (voter.getAge() < 18) {
            return "age should be greater than 18";
        } else {
            try {
                if (fileName.contains("..")) {
                    throw new Exception("file name is invalid" + fileName);
                }
                downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/image/")
                        .path(voter.getVoterId())
                        .toUriString();
                String finalDownloadUrl = downloadUrl;
                String query = "insert into voter(voter_name,aadhar_number,address,age,gender,mobile_number,voter_id,ward_no,profileImage,profileUrl) values(?,?,?,?,?,?,?,?,?,?)";
                jdbcTemplate.update(query, voter.getVoterName(), voter.getAadharNumber(), voter.getAddress(), voter.getAge(), voter.getGender(), voter.getMobileNumber(), voter.getVoterId(), voter.getWardNo(), voter.getProfileImage().getBytes(), finalDownloadUrl);
                return "successfully registered";

            } catch (Exception e) {
                System.out.println("could not save file" + fileName);
                System.out.println(e);
                return "duplicate entry / voter already registered";
            }
        }


    }

    @Override
    public byte[] getMyProfilePhoto(String voterId)
    {
        String get_image = "select profileImage from voter where voter_id='" + voterId + "'";
        return jdbcTemplate.queryForObject(get_image,byte[].class);
    }
    @Override
    public String getImageUrl(String voterId) {
        try {
            String url = "select profileUrl from voter where voter_id='" + voterId + "'";
            return jdbcTemplate.queryForObject(url, String.class);
        } catch (Exception e) {
            return "enter valid voterId";
        }
    }

    @Override
    public Voter viewProfile(String voterId) {
        try {

            String query = "select voter_id,voter_name,gender,age,ward_no,aadhar_number,mobile_number,address,profileUrl from voter where voter_id='" + voterId + "'";
            return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Voter.class));

        }catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("some entries are not valid");
            return null;
        }
    }
    @Override
    public String updateVoterInformation(String voterId, String mobileNumber, String address)
    {
        try {
            String select = "select count(voter_id) from voter where voter_id='" + voterId + "'";
            int count = jdbcTemplate.queryForObject(select, Integer.class);
            if (count > 0) {
                String check = "select mobile_number from voter where voter_id='" + voterId + "'";
                String mobNumber=jdbcTemplate.queryForObject(check,String.class);
                String check1="select address from voter where voter_id='" + voterId + "'";
                String adress=jdbcTemplate.queryForObject(check1, String.class);
                if (mobNumber.equals(mobileNumber) && adress.equalsIgnoreCase(address)) {
                    return "new entries same as the previous entries";
                }
                String query = "update voter set mobile_number='" + mobileNumber + "', address='" + address + "' where voter_id='" + voterId+"'";
                jdbcTemplate.update(query);
                return "details updated successfully";
            }
            return "voter id not found";
        }catch (Exception e)
        {
            System.out.println(e);
            return "update failed";
        }
    }


    @Override
    public String createElection(Election election) {
        jdbcTemplate.update("INSERT INTO election(election_name,start_date,end_date,instruction) VALUES(?,?,?,?)", election.getElectionName(), election.getStartDate(), election.getEndDate(), election.getInstruction());
        return "election registered";

    }

    @Override
    public List<Election> findElections() {
        String query = "select election_name,start_date,end_date from Election";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Election.class));
    }

    @Override
    public List<Election> findElectionByName(String name) {
        try {
            String query1="select count(election_name) from election where election_name='"+name+"'";
            int count=jdbcTemplate.queryForObject(query1, Integer.class);
            if(count>0) {
                String query = "select election_name, start_date,end_date from election where election_name='" + name + "'";
                return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Election.class));
            }
            return null;
        } catch (Exception e) {
            System.out.println("election name does not found");
            return null;
        }
    }

    @Override
    public String updateElectionSchedule(Election election) {

        try {
            String select_query="select count(election_name) from election where election_name='"+election.getElectionName()+"'";
            int count =jdbcTemplate.queryForObject(select_query, Integer.class);
            if(count>0) {
                String query = "update election set start_date ='" + election.getStartDate() + "',end_date='" + election.getEndDate() + "',instruction='"+election.getInstruction()+"' where election_name='" + election.getElectionName() + "'";
                jdbcTemplate.update(query);
                return "Election Rescheduled";
            }
            return "enter correct election name";
        } catch (Exception e) {
            return "enter correct election name";
        }

    }

    @Override
    public List<Election> findUpcomingElections() {
        try {
            LocalDateTime date = LocalDateTime.now();
            String query = "select election_name,start_date,end_date from election where start_date >'" + date + "'  order by start_date asc ";
            return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Election.class));
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    @Override
    public String addParties(Party party) {
        String fileName = StringUtils.cleanPath(party.getPartyLogo().getOriginalFilename());
        String downloadUrl;
        try {
            if (fileName.contains("..")) {
                throw new Exception("file name is invalid" + fileName);
            }
            downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/vote/logo/")
                    .path(party.getPartyName())
                    .toUriString();
            String finalDownloadUrl = downloadUrl;
            jdbcTemplate.update("INSERT INTO party(party_name,party_logo,logoUrl) VALUES(?,?,?)", party.getPartyName(), party.getPartyLogo().getBytes(), finalDownloadUrl);
            return "party registered successfully";
        } catch (Exception e) {

            System.out.println(e);
            return "party already exist";
        }

    }


    //    ***************************** NOMINATION ****************
    @Override
    public String newPartyRegistration(NewPartyRegistration newPartyRegistration) {
        String fileName = StringUtils.cleanPath(newPartyRegistration.getPartyLogo().getOriginalFilename());
        String downloadUrl;
        try {
            if (fileName.contains("..")) {
                throw new Exception("file name is invalid" + fileName);
            }
            downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/vote/logo/")
                    .path(newPartyRegistration.getPartyName())
                    .toUriString();
            String finalDownloadUrl = downloadUrl;
            jdbcTemplate.update("INSERT INTO party(party_name,party_logo,logoUrl) VALUES(?,?,?)", newPartyRegistration.getPartyName(), newPartyRegistration.getPartyLogo().getBytes(), finalDownloadUrl);
            jdbcTemplate.update("INSERT INTO candidate(candidate_name,candidate_gender,candidate_age,ward_no,voter_id) VALUES(?,?,?,?,?)",
                    newPartyRegistration.getCandidateName(), newPartyRegistration.getCandidateGender(), newPartyRegistration.getCandidateAge(), newPartyRegistration.getWardNo(), newPartyRegistration.getVoterId());
            jdbcTemplate.update("INSERT INTO partyRegistration(voter_id,party_name,election_name) VALUES(?,?,?)", newPartyRegistration.getVoterId(), newPartyRegistration.getPartyName(), newPartyRegistration.getElectionName());
            return "New Party Registration Done";
        } catch (Exception e) {
            System.out.println(e);
            return "Failed";
        }
    }

    @Override
    public byte[] getPartyLogo(String partyName)
    {
        String get_image = "select party_logo from party where party_name='" + partyName + "'";
        return jdbcTemplate.queryForObject(get_image,byte[].class);
    }



    @Override
    public String getLogoUrl(String partyName)
    {

            try {
                String url = "select logoUrl from  party where party_name='" + partyName + "'";
                return jdbcTemplate.queryForObject(url, String.class);
            }
            catch (Exception e)
            {
                return "Party does not exist";
            }

    }



    @Override
    public List<Party> getParties() {
        return jdbcTemplate.query("SELECT party_name,logoUrl FROM party", new BeanPropertyRowMapper<>(Party.class));
    }

    @Override
    public String existingPartyRegistration(ExistingPartyRegistration existingPartyRegistration) {
        try {
            jdbcTemplate.update("INSERT INTO candidate(candidate_name,candidate_gender,candidate_age,ward_no,voter_id) VALUES(?,?,?,?,?)",
                    existingPartyRegistration.getCandidateName(), existingPartyRegistration.getCandidateGender(), existingPartyRegistration.getCandidateAge(), existingPartyRegistration.getWardNo(), existingPartyRegistration.getVoterId());
            jdbcTemplate.update("INSERT INTO partyRegistration(voter_id,party_name,election_name) VALUES(?,?,?)", existingPartyRegistration.getVoterId(), existingPartyRegistration.getPartyName(), existingPartyRegistration.getElectionName());
            return "Candidate Registration For the Existing Party Done";
        } catch (Exception e) {
            System.out.println(e);
            return "Failed";
        }
    }
    @Override
    public List<Candidate> findAllCandidates() {
        String query = "select * from candidate";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Candidate.class));
    }

    @Override
    public List<Candidate> findCandidateByName(String name) {
            String query = "select voter_id as voterId,candidate_name,candidate_gender,candidate_age,ward_no from candidate where candidate_name='" + name+"'";
            return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Candidate.class));
    }

    @Override
    public List<PartyRegistration> findAllCandidatesInParty()
    {
        String query="select election_name,candidate.ward_no as wardNumber,partyRegistration.party_name,candidate_name from partyRegistration inner join candidate on partyRegistration.voter_id=candidate.voter_id";
        return jdbcTemplate.query(query,new BeanPropertyRowMapper<>(PartyRegistration.class));
    }


    //    ******************************************Voting****************************
    @Override
    public VotingPanelResponse getVotingPanel(String electionName) {
        try {
            VotingPanelResponse votingPanel = jdbcTemplate.queryForObject("SELECT election_name,start_date as electionDate from election where election_name = ?", new BeanPropertyRowMapper<>(VotingPanelResponse.class), electionName);
            List<VotingResponse> parties = jdbcTemplate.query("SELECT party.logoUrl as partyLogo,party.party_name from party INNER JOIN partyRegistration ON party.party_name = partyRegistration.party_name and election_name = ?", new BeanPropertyRowMapper<>(VotingResponse.class), electionName);
            votingPanel.setParties(parties);
            return votingPanel;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("election name not found");
            return null;
        }
    }

    @Override
    public String CastVote(Vote vote) {
        try {
            LocalDateTime now = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(now);
            String select_query="select start_date from election where election_name='"+vote.getElectionName()+"'";
            String select_query1="select end_date from election where election_name='"+vote.getElectionName()+"'";
            Timestamp match=jdbcTemplate.queryForObject(select_query,Timestamp.class);
            Timestamp match1=jdbcTemplate.queryForObject(select_query1,Timestamp.class);
            if(timestamp.after(match) && timestamp.before(match1)) {
                String query = "select ward_no from voter where voter_id='" + vote.getVoterId() + "'";
                int ward_no = jdbcTemplate.queryForObject(query, Integer.class);
                String tfaCode =String.valueOf(new Random().nextInt(9999)+1000);
                if (ward_no == vote.getWardNo()) {
                    //check if party is registered
                    String check_query = "select party_name from partyRegistration where party_name='" + vote.getPartyName() + "' and voter_id='" + vote.getVoterId() + "'";
                    jdbcTemplate.queryForObject(check_query, String.class);
//                    smsService.sendSms(vote.getMobileNumber(),tfaCode);
//                    if(checkCode(vote.getMobileNumber(),vote.getCode()))
//                    {
                        jdbcTemplate.update("INSERT INTO vote(voter_" +
                                "id,party_name,election_name,ward_no) VALUES(?,?,?,?)", vote.getVoterId(), vote.getPartyName(), vote.getElectionName(), vote.getWardNo());
                        String upQuery = "update partyRegistration set vote_count=vote_count+1 where party_name='" + vote.getPartyName() + "' and election_name='" + vote.getElectionName() + "' ";
                        jdbcTemplate.update(upQuery);
                        return "You have been Voted to " + vote.getPartyName();
//                    }
//                    else
//                    {
//                        return "you have entered wrong otp";
//                    }
                }
                return "Enter the Valid Ward Number";
            }
            return "date does not match";

        } catch (Exception e) {
            System.out.println(e);
            return "failed";
        }
    }
    @Override
    public String announceResultDate(String electionName, Timestamp result_date)
    {
        try {
            int count = jdbcTemplate.queryForObject("select count(election_name) from election where election_name='" + electionName + "'", Integer.class);
            if (count > 0) {
                String query = "update election set result_date='" + result_date + "' where election_name='" + electionName + "'";
                jdbcTemplate.update(query);
                return "result date announced";
            }
            return electionName+ "election name does not exist";
        }catch (Exception e)
        {
            System.out.println(e);
            return null;
        }
    }


    //    ******************************RESULT*******************************
    @Override
    public List<Election> getAllResult()
    {
        try {
            LocalDateTime now = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(now);
            String query = "select election_name,result_date from election where result_date<'"+timestamp+"'";
            return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Election.class));
        }catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
    }
    @Override
    public ResultResponse getResult(String electionName) {
        try {
            try {
                Integer maxVote = jdbcTemplate.queryForObject("select max(vote_count) from partyRegistration ", Integer.class);
                ResultResponse resultResponse = jdbcTemplate.queryForObject("select voter.profileUrl, candidate.candidate_name,vote_count as votes,partyRegistration.election_name from partyRegistration INNER JOIN candidate on partyRegistration.voter_id = candidate.voter_id and election_name = ? and partyRegistration.vote_count = ? inner join voter on voter.voter_id=candidate.voter_id", new BeanPropertyRowMapper<>(ResultResponse.class), electionName, maxVote);
                List<PartyResultResponse> partyResult = jdbcTemplate.query("SELECT logoUrl as partyLogo,candidate_name,partyRegistration.party_name,vote_count  as votes from partyRegistration inner join party on party.party_name = partyRegistration.party_name inner join candidate on candidate.voter_id = partyRegistration.voter_id and election_name = ? order by vote_count desc", new BeanPropertyRowMapper<>(PartyResultResponse.class), electionName);
                resultResponse.setPartyResult(partyResult);
                return resultResponse;
            }
            catch (Exception ex)
            {
                ResultResponse resultResponse = new ResultResponse();
                List<PartyResultResponse> partyResult = jdbcTemplate.query("SELECT logoUrl as partyLogo,candidate_name,partyRegistration.party_name,vote_count  as votes from partyRegistration inner join party on party.party_name = partyRegistration.party_name inner join candidate on candidate.voter_id = partyRegistration.voter_id and election_name = ? order by vote_count desc", new BeanPropertyRowMapper<>(PartyResultResponse.class), electionName);
                resultResponse.setPartyResult(partyResult);
                return resultResponse;
            }
        } catch (Exception e) {
            System.out.println("not found");
            System.out.println(e);
            return null;
        }
    }


}
