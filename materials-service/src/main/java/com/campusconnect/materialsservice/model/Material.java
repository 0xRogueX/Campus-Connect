package com.campusconnect.materialsservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Document(collection = "materials")
public class Material {
    @Id
    private String id;

    @NotEmpty(message = "Title cannot be empty")
    private String title;

    private String author;
    private String description;
    private Set<String> tags; // e.g., ["math", "pdf", "lecture"]

    private String fileId;  // GridFS file identifier
    private String fileName;
}
