package com.campusconnect.courseservice.repository;
import com.campusconnect.courseservice.model.entity.Syllabus;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface SyllabusRepository extends MongoRepository<Syllabus, String> {
    List<Syllabus> findBySubjectId(String subjectId);
}
