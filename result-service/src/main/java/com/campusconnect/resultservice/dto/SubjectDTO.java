package com.campusconnect.resultservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {
    private Integer subjectId;
    private Integer subjectCode;
    private String subjectName;
    private Boolean isAbsent;
    private String subjectGrade;
    private Integer credit;
    private String tese ;
    private String tpaca ;
    private String ttotal ;
    private String pesa ;
    private String ppaca ;
    private String ptotal ;
    private Integer resultId;
}