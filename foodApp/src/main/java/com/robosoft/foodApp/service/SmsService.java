package com.robosoft.foodApp.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    private static final String ACCOUNT_SID = "AC6779fe6a9ed10a541021bed30e31e827";
    private static final String AUTH_ID = "8ebc287ec9ddbf092268a570e9f2c35f";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_ID);
    }

    public boolean sendSms(String mobileNumber, String tfaCode) {
        Message.creator(new PhoneNumber(mobileNumber), new PhoneNumber("+12058523361"),
                "Your Two Factor Authentication code is:" + tfaCode).create();
        return true;
    }
}
