package com.robosoft.voterApp.service;

import com.robosoft.voterApp.model.Admin;
import com.robosoft.voterApp.model.Election;

import java.util.List;

public interface UserService {
    String userRegister(Admin admin);
    int userSignIn(Long mobNumber,String password);
    List<Election> findUpcomingElections(int sId);

    
}
