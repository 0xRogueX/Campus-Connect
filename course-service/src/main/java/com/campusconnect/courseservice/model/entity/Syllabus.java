package com.campusconnect.courseservice.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "syllabi")
public class Syllabus {
    @Id
    private String id;
    private String subjectId;    // foreign key to Subject
    private int week;
    private List<String> topics;
}
