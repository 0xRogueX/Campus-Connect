package com.campusconnect.resultservice.repository;

import com.campusconnect.resultservice.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@EnableJpaRepositories
public interface ResultRepository extends JpaRepository<Result,Integer> {
Result findResultByStudentId(String studentId );
Result findResultByEnrollmentNo(Long enrollmentNo);

    Optional<Result> findByEnrollmentNo(Long enrollmentNo);


}
