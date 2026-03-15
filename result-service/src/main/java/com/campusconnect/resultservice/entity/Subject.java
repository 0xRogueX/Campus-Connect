package com.campusconnect.resultservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subject")
public class Subject extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subjectId;

    private Integer subjectCode;
    private String subjectName;
    private Boolean isAbsent;
    private String subjectGrade;
    private Integer credit;

    @Column(name = "tese")
    private String tese = subjectGrade;

    @Column(name = "tpaca")
    private String tpaca =subjectGrade;

    @Column(name = "ttotal")
    private String ttotal = subjectGrade;

    @Column(name = "pesa")
    private String pesa =subjectGrade;

    @Column(name = "ppaca")
    private String ppaca = subjectGrade;

    @Column(name = "ptotal")
    private String ptotal =subjectGrade;

    @ManyToOne
    @JoinColumn(name = "result_id")
    private Result result;
}

enum SubjectGrade {
    AA,AB,BB,BC,CC,CD,DD,DE,FF
}