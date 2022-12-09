package com.robosoft.votingAppMiniProject.controller;


import com.robosoft.votingAppMiniProject.entity.*;
import com.robosoft.votingAppMiniProject.request.ExistingPartyRegistration;
import com.robosoft.votingAppMiniProject.request.NewPartyRegistration;
import com.robosoft.votingAppMiniProject.response.PartyResultResponse;
import com.robosoft.votingAppMiniProject.response.ResultResponse;
import com.robosoft.votingAppMiniProject.response.VotingPanelResponse;
import com.robosoft.votingAppMiniProject.service.EmailService;
import com.robosoft.votingAppMiniProject.service.SmsService;
import com.robosoft.votingAppMiniProject.service.VoterServiceImpl;
import com.robosoft.votingAppMiniProject.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/vote")
public class VoterController {
    @Autowired
    VoterServiceImpl voterService;

    @Autowired
    private AuthenticationManager authenticationManager;



    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    EmailService emailService;

    @Autowired
    SmsService smsService;


    @PutMapping("/user/send2faCodeInEmail")
    public ResponseEntity<Object> send2faCodeInEmail(@RequestParam String mobileNumber, @RequestParam String email) throws MessagingException {
        String tfaCode =String.valueOf(new Random().nextInt(9999)+1000);
        emailService.sendEmail(email,tfaCode);
        voterService.update2FAProperties(mobileNumber,tfaCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/user/send2faCodeInSMS/{email}")
    public ResponseEntity<Object> send2faCodeInSMS(@RequestBody String mobileNumber,@PathVariable  String email)
    {
        System.out.println(mobileNumber);
        String tfaCode =String.valueOf(new Random().nextInt(9999)+1000);
        smsService.sendSms(mobileNumber,tfaCode);
        voterService.update2FAProperties(email,tfaCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/verify")
    public ResponseEntity<Object> verify(@RequestParam String mobileNumber, @RequestParam String code,@RequestParam String password)
    {
        boolean isValid=voterService.checkCode(mobileNumber,code,password);
        if(isValid)
        {
            return  new ResponseEntity<>(HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    @PostMapping("/authenticate")
    public JWTResponse authenticate(@RequestBody JWTRequest jwtRequest) throws Exception
    {

        try
        {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getMobileNumber(),
                            jwtRequest.getPassword()
                    )
            );
        }
        catch (BadCredentialsException e)
        {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails = voterService.loadUserByUsername(jwtRequest.getMobileNumber());

        final String token = jwtUtility.generateToken(userDetails);

        return  new JWTResponse(token);
    }

    @PostMapping("/userSignUp")
    public ResponseEntity<String> userRegister(@RequestBody User user){
        try {
            String s = voterService.userRegister(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(s);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Registration Failed",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/wardRegister")
    public ResponseEntity<String> wardRegister(@RequestBody Ward ward)
    {
        try{
            return new ResponseEntity<>(voterService.wardRegister(ward),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("registration failed",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/viewAllWards")
    public ResponseEntity<List<Ward>> getAllWards() {
        if(voterService.findAllWards()!=null) {
            return new ResponseEntity<>(voterService.findAllWards(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);



    }

    @GetMapping("/viewWardByName")
    public ResponseEntity<List<Ward>> getWardByName(@RequestParam String wardName) {
        if(voterService.findWardByName(wardName).size()>0){
            return new ResponseEntity<>(voterService.findWardByName(wardName), HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

    }

    @PostMapping("/voterRegister")
    public ResponseEntity<String> voterRegister(@ModelAttribute Voter voter)
    {
        if(voterService.voterRegister(voter).equalsIgnoreCase("duplicate entry / voter already registered"))
        {
            return new ResponseEntity<>("voter already registered/duplicate entry ",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("voter registered successfully",HttpStatus.CREATED);

    }

    @GetMapping("/viewProfileUrl")
    public ResponseEntity<String> getProfileUrl(@RequestParam String voterId)
    {
        return new ResponseEntity<>(voterService.getImageUrl(voterId), HttpStatus.OK);
    }
    @GetMapping("/image/{voterId}")
    public ResponseEntity<Resource> getMyProfilePhoto(@PathVariable String voterId) throws IOException
    {
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/png")).header("Content-Disposition","filename=\"" + voterId + ".png" +"\"").body(new ByteArrayResource(voterService.getMyProfilePhoto(voterId)));
    }

    @GetMapping("/viewProfile/{voterId}")
    public ResponseEntity<Voter> getProfile(@PathVariable String voterId){
        if(voterService.viewProfile(voterId)==null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(voterService.viewProfile(voterId), HttpStatus.OK);



    }
    @PatchMapping("/updateVoterInformation")
    public ResponseEntity<?> updateVoterInformation(@RequestParam String voterId,@RequestParam String mobileNumber,@RequestParam String address)
    {
       if(voterService.updateVoterInformation(voterId, mobileNumber, address).equalsIgnoreCase("details updated successfully")) {
           return new ResponseEntity<>("details updated successfully", HttpStatus.OK);
       }
       return new ResponseEntity<>("update failed because new entries same as the previous entries",HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/createElection")
    public ResponseEntity<String> createElection(@RequestBody Election election)
    {
        System.out.println(election);
        try{
            return new ResponseEntity<>(voterService.createElection(election),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Registration failed",HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/viewElections")
    public ResponseEntity<List<Election>> getElections()
    {
        if(voterService.findElections()!=null) {
            return new ResponseEntity<>(voterService.findElections(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }
    @GetMapping("/findElectionByName/{name}")
    public ResponseEntity<List<Election>> findElectionByName(@PathVariable String name)
    {
        if(voterService.findElectionByName(name)!=null) {
            return new ResponseEntity<>(voterService.findElectionByName(name), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }
    @PutMapping("/updateElectionSchedule")
    public ResponseEntity<String> updateElectionSchedule(@RequestBody Election election){
        if(voterService.updateElectionSchedule(election).equalsIgnoreCase("enter correct election name"))
        {
            return new ResponseEntity<>("enter correct election name",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("election rescheduled",HttpStatus.OK);

    }
    @GetMapping("/Home")
    public ResponseEntity<List<Election>> findUpcomingElections(){
        if(voterService.findUpcomingElections()!=null) {
            return new ResponseEntity<>(voterService.findUpcomingElections(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PostMapping("/registerToParty")
    public ResponseEntity<String> addParties(@ModelAttribute Party party)
    {
        if(voterService.addParties(party).equalsIgnoreCase("party registered successfully")){

            return  new ResponseEntity<>("party registered successfully",HttpStatus.CREATED);
        }
         return new ResponseEntity<>("party already exist",HttpStatus.BAD_REQUEST);

    }
    @PostMapping("/registerToNewParty")
    public ResponseEntity<String> newPartyRegistration(@ModelAttribute NewPartyRegistration newPartyRegistration)
    {
        if(voterService.newPartyRegistration(newPartyRegistration).equalsIgnoreCase("New Party Registration Done")) {
            return new ResponseEntity<>("New Party Registration Done", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("failed",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/logoUrl")
    public ResponseEntity<String> getLogoUrl(@RequestParam String partyName)
    {
        if(voterService.getLogoUrl(partyName)!=null) {
            return new ResponseEntity<>(voterService.getLogoUrl(partyName), HttpStatus.OK);
        }
        return new ResponseEntity<>("empty profile", HttpStatus.NOT_FOUND);
    }
    @GetMapping("/logo/{partyName}")
    public ResponseEntity<Resource> getPartyLogo(@PathVariable String partyName)
    {
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/png")).header("Content-Disposition","filename=\"" + partyName + ".png" +"\"").body(new ByteArrayResource(voterService.getPartyLogo(partyName)));
    }
    @GetMapping("/viewParties")
    public ResponseEntity<List<Party>> getParties()
    {
        if(voterService.getParties()!=null) {
            return new ResponseEntity<>(voterService.getParties(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

    }
    @PostMapping("/existingPartyRegistration")
    public ResponseEntity<String> existingPartyRegistration(@RequestBody ExistingPartyRegistration existingPartyRegistration)
    {
        if(voterService.existingPartyRegistration(existingPartyRegistration).equalsIgnoreCase("Candidate Registration For the Existing Party Done")) {
            return new ResponseEntity<>("Candidate Registration For the Existing Party Done", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("candidate already exist in the party",HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/viewAllCandidate")
    public ResponseEntity<?> getAllCandidates(){
        if(voterService.findAllCandidates().size()>0)
        {
            return new ResponseEntity<>(voterService.findAllCandidates(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

    }
    @GetMapping("/viewCandidateByName/{name}")
    public ResponseEntity<List<Candidate>> getCandidateByName(@PathVariable String name){
        if(voterService.findCandidateByName(name).size()>0) {
            return new ResponseEntity<>(voterService.findCandidateByName(name), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @GetMapping("/viewCandidatesInParty")
    public ResponseEntity<List<PartyRegistration>> findAllCandidatesInParty() {
        if(voterService.findAllCandidatesInParty().size()>0){
            return new ResponseEntity<>(voterService.findAllCandidatesInParty(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    @GetMapping("/viewVotingPanel/{electionName}")
    public ResponseEntity<VotingPanelResponse> getVotingPanel(@PathVariable String electionName)
    {
        if(voterService.getVotingPanel(electionName)!=null) {
            return new ResponseEntity<>(voterService.getVotingPanel(electionName), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @PostMapping("/CastVote/{voterId}")
    public ResponseEntity<String> CastVote(@RequestBody Vote vote)
    {
        return new ResponseEntity<>(voterService.CastVote(vote), HttpStatus.CREATED);
    }
    @PatchMapping("/updateResultDate/{electionName}")
    public ResponseEntity<?> announceResultDate(@PathVariable String electionName, @RequestParam String result_date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date parsedDate = (Date) dateFormat.parse(result_date);
        Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
        return new ResponseEntity<>(voterService.announceResultDate(electionName, timestamp),HttpStatus.OK);
    }
    @GetMapping("/viewAllResult")
    public ResponseEntity<?> getAllResult()
    {
        if(voterService.getAllResult()!=null) {
            return new ResponseEntity<>(voterService.getAllResult(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/viewResult/{electionName}")
    public ResponseEntity<?> getResult(@PathVariable String electionName) {
        ResultResponse response=voterService.getResult(electionName);
        if (response.getPartyResult().size()!= 0) {
            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wrong Entry");

    }


}
