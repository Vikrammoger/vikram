package com.robosoft.votingAppMiniProject.service;
import org.springframework.stereotype.Service;
import javax.mail.*;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService
{
    private static final String username="mogervikram1@gmail.com";
    private static String password="dctzzdbwanofywki";
    public boolean sendEmail(String email, String tfaCode) throws MessagingException {
        Properties props= new Properties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port","587");

        Session session= Session.getInstance(props, new javax.mail.Authenticator()
        {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication(){
                return  new PasswordAuthentication(username,password);
            }
        });

        MimeMessage message= new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(email));

        message.setSubject("Two Factor Authentication code from our Service");
        message.setText("Your Two Factor Authentication code is:"+tfaCode);
        Transport.send(message);
        return true;
    }
}
