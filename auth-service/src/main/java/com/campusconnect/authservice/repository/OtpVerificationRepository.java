package com.campusconnect.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.campusconnect.authservice.model.entity.OtpVerification;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;
@EnableJpaRepositories
public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {
    Optional<OtpVerification> findTopByEmailOrderByExpiryTimeDesc(String email);
}