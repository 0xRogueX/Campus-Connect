package com.campusconnect.authservice.OTP;/*
package com.CampusConnect.Auth.OTP;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    private static final String ACCOUNT_SID = "AC5e213b36211a080d1d1c10bec50fdc52";
    private static final String AUTH_TOKEN = "6cf261f7d0cf578c01eb16a8a0c66cd4";
    private static final String FROM_PHONE = "+919824282916";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendOTP(String toPhoneNumber, String otp) {
        Message message = Message.creator(
                        new PhoneNumber(toPhoneNumber),
                        new PhoneNumber(FROM_PHONE),
                        "Your OTP is: " + otp)
                .create();
    }
}
*/
