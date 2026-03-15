package com.campusconnect.examservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDTO {
    private Long id;
    private String examName;
    private LocalDateTime examStart;
    private LocalDateTime examEnd;
    private Integer examDuration;
    private String semester;
    private String branch;
    private Set<ExamSubjectDTO> subjects = new HashSet<>();
}