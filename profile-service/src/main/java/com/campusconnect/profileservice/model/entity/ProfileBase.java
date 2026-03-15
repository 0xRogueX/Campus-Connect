package com.campusconnect.profileservice.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "profiles")
public abstract class ProfileBase {
    @Id
    private String id;
    private String userId;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private String dateOfBirth;
}