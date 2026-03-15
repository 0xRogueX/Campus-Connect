package com.campusconnect.authservice.OTP;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;


@Service
public class OTPService {

    private static final String CHARACTER = "0123456789";
    private static final int OTP_LENGTH = 6;

    public String generateOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        for(int i=0; i < OTP_LENGTH;i++){
            otp.append(CHARACTER.charAt(random.nextInt(CHARACTER.length())));
        }
        return otp.toString();
    }
}
