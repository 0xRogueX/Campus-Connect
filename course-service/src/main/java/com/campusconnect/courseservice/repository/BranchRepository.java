package com.campusconnect.courseservice.repository;
import com.campusconnect.courseservice.model.entity.Branch;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BranchRepository extends MongoRepository<Branch, String> {

}
