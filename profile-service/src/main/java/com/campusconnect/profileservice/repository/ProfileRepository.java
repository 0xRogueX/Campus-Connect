package com.campusconnect.profileservice.repository;

import com.campusconnect.profileservice.model.entity.ProfileBase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends MongoRepository<ProfileBase, String> {
    Optional<ProfileBase> findByUserId(String userId);
    void deleteByUserId(String userId);
}