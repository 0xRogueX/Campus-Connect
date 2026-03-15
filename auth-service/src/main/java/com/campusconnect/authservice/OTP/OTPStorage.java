package com.campusconnect.authservice.OTP;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


public class OTPStorage {
    private static final ConcurrentHashMap<String,OTPData> otpCache = new ConcurrentHashMap<>();
    public static void  storeOTP(String phoneNumber, String otp) {
        otpCache.put(phoneNumber,new OTPData(otp,System.currentTimeMillis()));
    }

    public static boolean validateOTP(String phoneNumberOrEmail, String otp) {
        OTPData otpData = otpCache.get(phoneNumberOrEmail);
        if(otpData!=null && otpData.getOtp().equals(otp)){
            long expiryTime = TimeUnit.MINUTES.toMillis(5);
            return System.currentTimeMillis() - otpData.getTimestamp() <= expiryTime;
        }
        return false;
    }
}
