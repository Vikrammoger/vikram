package com.robosoft.voterApp.service;

import com.robosoft.voterApp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private int sessionId;
    @Override
    public String adminRegister(Admin admin) {

            String query = "insert into admin (adminName,email,mobNumber,password) values('" + admin.getAdminName() + "','" + admin.getEmail() + "'," + admin.getMobNumber() + ",'" + admin.getPassword() + "')";
            jdbcTemplate.update(query);
            return "Admin Registered Successfully";
    }

    @Override
    public int adminSignIn(Long mobNumber, String password) {
        try {
            String query = "select mobNumber,password from admin where mobNumber=" + mobNumber + " and password='" + password + "'";
            jdbcTemplate.queryForObject(query,new BeanPropertyRowMapper<>(Admin.class));
            sessionId = new Random().nextInt(1, 1000);
            return sessionId;
        } catch (Exception e) {
            System.out.println("invalid credentials");
            return 0;
        }
    }

    @Override
    public String addElections(int sId, Election election) {

            if (sId == sessionId) {
                try {
                    String query = "insert into election values('" + election.getElectionName() + "','" + election.getStartDateAndTime() + "','" + election.getEndDateAndTime() + "')";
                    jdbcTemplate.update(query);
                    return "Election Added";
                } catch (Exception e) {
                    return "duplicate entry";
                }
            }
            return "enter valid session id";

    }

    @Override
    public List<Election> findAllElections(int sId) {
        if(sId==sessionId) {
            String query = "select * from election";
            return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Election.class));
        }
        return null;
    }

    @Override
    public List<Election> findElectionByName(int sId, String name) {
        if(sId==sessionId) {
            String query = "select * from election where electionName='" + name+"'";
            return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Election.class));
        }
        return null;
    }

    @Override
    public String updateElectionSchedule(int sId, Election election) {
        if(sId==sessionId)
        {
            try {
                System.out.println(election.getStartDateAndTime()+"  "+election.getEndDateAndTime()+" "+election.getElectionName());
                String query = "update election set startDateAndTime ='" + election.getStartDateAndTime() + "',endDateAndTime='" + election.getEndDateAndTime() + "' where electionName='" + election.getElectionName() + "'";
                jdbcTemplate.update(query);
                return "Election Rescheduled";
            }catch (Exception e){
                return "enter correct election name";
            }
        }
        return "enter valid session id";
    }

    @Override
    public List<Election> findUpcomingElections(int sId) {
        if(sId==sessionId)
        {
            LocalDateTime date = LocalDateTime.now();
            String query="select * from election where startDateAndTime>'"+date+"'  order by startDateAndTime asc " ;
            return jdbcTemplate.query(query,new BeanPropertyRowMapper<>(Election.class));
        }
        return null;
    }


//    public Boolean authentication(int sId)
//    {
//        if(sId==sessionId)
//        {
//            return true;
//        }
//        return false;
//    }

    @Override
    public String addWard(int sId, Ward ward) {
        if(sId==sessionId) {

            String query = "insert into ward values(" + ward.getWardNumber() + ",'" + ward.getWardName() + "')";
            jdbcTemplate.update(query);
            return "ward Added";
        }
        return "duplicate entry";

    }

    @Override
    public List<Ward> findAllWards(int sId) {
        if(sId==sessionId) {
            String query = "select * from ward";
            return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Ward.class));
        }
        return null;
    }

    @Override
    public List<Ward> findWardByNumber(int sId, int wardNumber) {
        if(sId==sessionId) {
            String query = "select * from ward where wardNumber=" + wardNumber;
            return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Ward.class));
        }
        return null;
    }

    @Override
    public String deleteWard(int sId, int wardNumber) {
        if(sId==sessionId) {
            try {
                String query = "delete from ward where wardNumber=" + wardNumber;
                jdbcTemplate.update(query);
                return "ward removed successfully";
            }catch (Exception e){
                return "ward number does not exist";
            }
        }
        return "enter valid session id";
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
    public List<User> findAllVoters(int sId) {
        return null;
    }

    @Override
    public List<User> findAllUsers(int sId) {
        return null;
    }
}
