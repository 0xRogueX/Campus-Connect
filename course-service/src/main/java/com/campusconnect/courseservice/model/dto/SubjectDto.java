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
public class SubjectDto {
    private String id;

    @NotBlank(message = "Subject name is required")
    private String name;

    private int semester;

    private List<SyllabusDto> syllabus = new ArrayList<>();
}
