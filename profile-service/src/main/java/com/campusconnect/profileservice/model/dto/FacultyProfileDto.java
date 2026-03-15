package com.campusconnect.profileservice.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FacultyProfileDto extends BaseProfileDto {
    private String employeeId;
    private String department;
    private String designation;
}
