package com.campusconnect.profileservice.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StudentProfileDto extends BaseProfileDto {
    private String enrollmentNumber;
    private String course;
    private String batch;
}
