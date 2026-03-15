package com.campusconnect.courseservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto {
    private String id;

    @NotBlank(message = "Branch name is required")
    private String name;

    private List<SubjectDto> subjects = new ArrayList<>();
}