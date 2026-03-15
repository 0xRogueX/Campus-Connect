package com.campusconnect.eventsservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Event")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event extends BaseEntity {

    @Id
    private String eventId;
    private String title;
    private String content;
    private String contentImageUrl; // Store URL instead of File object
    private String postedBy;
}