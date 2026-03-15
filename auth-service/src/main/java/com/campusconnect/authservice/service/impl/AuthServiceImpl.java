package com.campusconnect.authservice.service.impl;

import com.campusconnect.authservice.OTP.EmailService;
import com.campusconnect.authservice.OTP.OTPService;
import com.campusconnect.authservice.OTP.OTPStorage;
import com.campusconnect.authservice.event.AuthEvent;
import com.campusconnect.authservice.mapper.UserMapper;
import com.campusconnect.authservice.model.dto.AuthResponse;
import com.campusconnect.authservice.model.dto.OTPRequest;
import com.campusconnect.authservice.model.dto.UserDTO;
import com.campusconnect.authservice.model.entity.User;
import com.campusconnect.authservice.repository.UserRepository;
import com.campusconnect.authservice.service.AuthService;
import com.campusconnect.authservice.config.JwtTokenProvider;
import com.campusconnect.authservice.exception.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepo;
    private final EmailService emailService;
    private final OTPService otpService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;




    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return UserMapper.toDTO(user);
    }
    @Override
    public UserDTO sendOTP(UserDTO userDTO) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword())
            );

            if (!authentication.isAuthenticated()) {
                throw new UnauthorizedAccessException("Invalid email or password");
            }

            User user = userRepo.findByEmail(userDTO.getEmail().trim().toLowerCase())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found for this Email:", "STUDENT" + userDTO.getId(), userDTO.getEmail()));

            String otp = "123456";
           // String otp = otpService.generateOTP();
           // emailService.sendOTP(user.getEmail(), otp);
            OTPStorage.storeOTP(user.getEmail(), otp);
            UserDTO userResponseDto = UserMapper.toDTO(user);

            AuthEvent authEvent = new AuthEvent(user.getEmail(), otp);
            log.info("Start sending Event to Kafka: {}", authEvent);
            log.info("End sending Event to Kafka: {}", authEvent);

            return userResponseDto;

        } catch (AuthenticationException ex) {
            throw new UnauthorizedAccessException("Invalid email or password");
        } catch (Exception ex) {
            log.error("Error while sending OTP: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to send OTP. Please try again later.");
        }
    }


    @Override
    public AuthResponse verify(OTPRequest otpRequest) {
        User user = userRepo.findByEmail(otpRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this Email: " ,"User", otpRequest.getEmail()));

        if( !OTPStorage.validateOTP(otpRequest.getEmail(), otpRequest.getOtp())){
            throw new UnauthorizedAccessException("Invalid email or password");
        };
        try {
            String token = tokenProvider.generateToken(user);
            UserDTO userResponseDto = UserMapper.toDTO(user);
            return new AuthResponse(token, "Authentication successful");

        } catch (RuntimeException e) {
            System.err.println("Authentication failed: " + e.getMessage());
            throw new UnauthorizedAccessException("Invalid email or password");
        }

    }
}
