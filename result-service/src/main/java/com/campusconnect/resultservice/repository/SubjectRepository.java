package com.campusconnect.resultservice.repository;

import com.campusconnect.resultservice.entity.Result;
import com.campusconnect.resultservice.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface SubjectRepository extends JpaRepository<Subject,Integer> {
    List<Subject> findByResultResultId(Integer resultId);

}