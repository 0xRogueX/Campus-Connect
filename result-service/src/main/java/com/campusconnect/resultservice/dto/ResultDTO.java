package com.campusconnect.resultservice.dto;

import com.campusconnect.resultservice.entity.Subject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {

    private Integer resultId;
    private Long enrollmentNo;
    private String semester;
    private String branch;
    private LocalDate declaredOn;
    private String exam;
    private Double spi;
    private Double cpi;
    private Double cgpa;
    private String studentId;
    private List<SubjectDTO> subjects;
    private Boolean isPass;

}