package com.robosoft.voterApp.controller;

import com.robosoft.voterApp.model.Admin;
import com.robosoft.voterApp.model.Election;
import com.robosoft.voterApp.model.User;
import com.robosoft.voterApp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @PostMapping("/userSignUp")

    public ResponseEntity<String> userSignUp(@RequestBody User user) {
        try {
            return new ResponseEntity<>(userService.userRegister(user), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("already registered", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/userSignIn/{mobNumber}/{password}")
    public ResponseEntity<String> userSignIn(@PathVariable Long mobNumber, @PathVariable String password) {

        return new ResponseEntity<>(userService.userSignIn(mobNumber, password), HttpStatus.OK);
    }
    @GetMapping("/Home")
    public ResponseEntity<List<Election>> findUpcomingElections(){
        try {
            return new ResponseEntity<>(userService.findUpcomingElections(), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
