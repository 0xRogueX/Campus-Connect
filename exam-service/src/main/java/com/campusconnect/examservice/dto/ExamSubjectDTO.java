package com.campusconnect.examservice.dto;


import com.campusconnect.examservice.entity.Exam;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamSubjectDTO {
    private Long id;
    private Long examId;
    private Long subjectId;
    private String subjectName;
    private String subjectCode;
    private Integer credits;
    private LocalDate examDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}