package com.campusconnect.examservice.repository;


import com.campusconnect.examservice.entity.ExamSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamSubjectRepository extends JpaRepository<ExamSubject, Long> {
    List<ExamSubject> findByExamId(Long examId);
    List<ExamSubject> findBySubjectId(Long subjectId);
}
