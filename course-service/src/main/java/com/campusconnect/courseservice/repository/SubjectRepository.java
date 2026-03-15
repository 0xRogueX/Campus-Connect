package com.campusconnect.courseservice.repository;
import com.campusconnect.courseservice.model.entity.Subject;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface SubjectRepository extends MongoRepository<Subject, String> {
    List<Subject> findByBranchId(String branchId);
}
