package com.campusconnect.profileservice.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminProfileDto extends BaseProfileDto {
    private String adminId;
    private String role;
}
