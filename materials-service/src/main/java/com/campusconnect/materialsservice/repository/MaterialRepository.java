package com.campusconnect.materialsservice.repository;

import com.campusconnect.materialsservice.model.Material;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MaterialRepository extends MongoRepository<Material, String> {
    Material findByFileId(String fileId);
}
