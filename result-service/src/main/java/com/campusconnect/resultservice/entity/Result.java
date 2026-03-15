package com.campusconnect.resultservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "result",    uniqueConstraints = {@UniqueConstraint(columnNames = {"enrollmentNo"})})
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Result extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resultId;

    @Column(unique = true)
    private Long enrollmentNo;
    private String semester;
    private String branch;

    private LocalDate declaredOn;
    private String exam;

    private String studentId;

    @OneToMany(mappedBy = "result", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects;

    private Double spi;
    @ElementCollection
    private List<Double> spis = new ArrayList<>();
    private Double cpi;
    private Double cgpa;

    private Boolean isPass;
}

