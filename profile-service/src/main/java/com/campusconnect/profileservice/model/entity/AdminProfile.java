package com.campusconnect.profileservice.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "profiles")
public class AdminProfile extends ProfileBase {
    private String adminId;
    private String role;
}