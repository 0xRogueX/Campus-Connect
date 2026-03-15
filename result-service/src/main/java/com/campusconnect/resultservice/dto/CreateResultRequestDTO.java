package com.campusconnect.resultservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateResultRequestDTO {
    private String studentId;
    private String exam;
    private List<SubjectDTO> subjects;
}