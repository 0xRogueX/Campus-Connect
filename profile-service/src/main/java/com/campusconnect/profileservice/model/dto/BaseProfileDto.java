package com.campusconnect.profileservice.model.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public abstract class BaseProfileDto {
    @NotBlank
    private String userId;

    @NotBlank
    private String fullName;

    private String email;
    private String phone;
    private String address;
    private String gender;
    private String dateOfBirth;
}
