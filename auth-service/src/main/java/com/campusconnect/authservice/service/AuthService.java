package com.campusconnect.authservice.service;

import com.campusconnect.authservice.model.dto.AuthResponse;
import com.campusconnect.authservice.model.dto.OTPRequest;
import com.campusconnect.authservice.model.dto.UserDTO;

public interface AuthService {


    UserDTO saveUser(UserDTO userDTO);
     AuthResponse verify(OTPRequest otpRequest);
     UserDTO sendOTP(UserDTO UserDTO);
}
