package com.campusconnect.eventsservice.service;

import com.campusconnect.eventsservice.dto.EventDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IEventService {

    /**
     * Create a new event with optional image upload
     */
    EventDTO createEvent(EventDTO eventDTO, MultipartFile imageFile) throws IOException;

    /**
     * Get all events
     */
    List<EventDTO> getAllEvents();

    /**
     * Get event by ID
     */
    EventDTO getEventById(String eventId);

    /**
     * Update an existing event with optional new image
     */
    EventDTO updateEvent(String eventId, EventDTO eventDTO, MultipartFile imageFile) throws IOException;

    /**
     * Delete an event and its associated image
     */
    boolean deleteEvent(String eventId);
}