package com.campusconnect.examservice.repository;

import com.campusconnect.examservice.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findBySemester(String semester);
    List<Exam> findByBranch(String branch);

}
