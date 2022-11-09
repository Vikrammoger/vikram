package com.robosoft.voterApp.service;

import com.robosoft.voterApp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements  UserService{
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public String userRegister(User user) {
        String query="insert into user(userName,mobNumber,password) values('"+user.getUserName()+"',"+user.getMobNumber()+",'"+user.getPassword()+"')";
        jdbcTemplate.update(query);
        return "user added successfully";
    }

    @Override
    public String userSignIn(Long mobNumber, String password) {
        try {
            String query = "select mobNumber,password from user where mobNumber=" + mobNumber + " and password='" + password + "'";
            jdbcTemplate.queryForObject(query,new BeanPropertyRowMapper<>(Admin.class));
        } catch (Exception e) {
            return "invalid credentials";
        }
        return "signed in";
    }

    @Override
    public List<Election> findUpcomingElections() {

            LocalDateTime date = LocalDateTime.now();
            String query="select * from election where startDateAndTime>'"+date+"'  order by startDateAndTime asc " ;
            return jdbcTemplate.query(query,new BeanPropertyRowMapper<>(Election.class));


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
