package com.robosoft.voterApp.controller;


import com.robosoft.voterApp.model.Admin;
import com.robosoft.voterApp.model.Election;
import com.robosoft.voterApp.model.Parties;
import com.robosoft.voterApp.model.Ward;
import com.robosoft.voterApp.service.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    AdminServiceImpl adminService;

    @PostMapping("/adminSignUp")

    public ResponseEntity<String> adminSignUp(@RequestBody Admin admin) {
        try {
            return new ResponseEntity<>(adminService.adminRegister(admin), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("already registered", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/adminSignIn/{mobNumber}/{password}")
    public ResponseEntity<Integer> adminSignIn(@PathVariable Long mobNumber, @PathVariable String password) {

        return new ResponseEntity<>(adminService.adminSignIn(mobNumber, password), HttpStatus.OK);
    }

    @PostMapping("/addWard/{sId}")
    public ResponseEntity<String> addWard(@PathVariable int sId, @RequestBody Ward ward) {
        try {
            return new ResponseEntity<>(adminService.addWard(sId, ward), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("ward already exist", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/viewAllWards/{sId}")
    public ResponseEntity<List<Ward>> getAllWards(@PathVariable int sId) {
        try {
            return new ResponseEntity<>(adminService.findAllWards(sId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/viewWardByNumber/{sId}/{wardNumber}")
    public ResponseEntity<List<Ward>> getWardByNumber(@PathVariable int sId, @PathVariable int wardNumber) {
        try {
            return new ResponseEntity<>(adminService.findWardByNumber(sId, wardNumber), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/removeWard/{sId}/{wardNumber}")
    public ResponseEntity<String> deleteWard(@PathVariable int sId, @PathVariable int wardNumber) {
            return new ResponseEntity<>(adminService.deleteWard(sId, wardNumber), HttpStatus.NO_CONTENT);
    }
    @PostMapping("/addElections/{sId}")
    public ResponseEntity<String> addElections(@PathVariable int sId,@RequestBody Election election) {
        return new ResponseEntity<>(adminService.addElections(sId, election), HttpStatus.CREATED);
    }
    @GetMapping("/viewAllElections/{sId}")
    public ResponseEntity<List<Election>> getAllElections(@PathVariable int sId) {
        try {
            return new ResponseEntity<>(adminService.findAllElections(sId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/viewElectionByName/{sId}/{name}")
    public ResponseEntity<List<Election>> getElectionByName(@PathVariable int sId, @PathVariable String name) {
        try {
            return new ResponseEntity<>(adminService.findElectionByName(sId, name), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @PutMapping("/updateElectionSchedule/{sId}")
    public ResponseEntity<String> updateElectionSchedule(@PathVariable int sId,@RequestBody Election election) {
        return new ResponseEntity<>(adminService.updateElectionSchedule(sId, election), HttpStatus.CREATED);
    }
    @GetMapping("/Home/{sId}")
    public ResponseEntity<List<Election>> findUpcomingElections(@PathVariable int sId){
        try {
            return new ResponseEntity<>(adminService.findUpcomingElections(sId), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
