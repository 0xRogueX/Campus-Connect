package com.campusconnect.eventsservice.controller;

import com.campusconnect.eventsservice.dto.EventDTO;
import com.campusconnect.eventsservice.repository.EventRepository;
import com.campusconnect.eventsservice.service.IEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final IEventService eventService;


    public EventController(IEventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Create a new event with optional image upload
     */


    @GetMapping("/healthcheck")
    public String healthCheck() {
        return "OK";
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventDTO> createEvent(
            @RequestPart("title") String title,
            @RequestPart("content") String content,
            @RequestPart("postedBy") String postedBy,

            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        EventDTO eventDTO = new EventDTO();
        eventDTO.setContent(content);
        eventDTO.setTitle(title);
        eventDTO.setPostedBy(postedBy);
        try {
            EventDTO createdEvent = eventService.createEvent(eventDTO, imageFile);
            return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get all events
     */
    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    /**
     * Get event by ID
     */
    @GetMapping("/{eventId}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable String eventId) {
        EventDTO event = eventService.getEventById(eventId);
        if (event != null) {
            return new ResponseEntity<>(event, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Update an existing event with optional new image
     */
    @PutMapping(value = "/{eventId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventDTO> updateEvent(
            @PathVariable String eventId,
            @RequestPart("event") EventDTO eventDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        try {
            EventDTO updatedEvent = eventService.updateEvent(eventId, eventDTO, imageFile);
            if (updatedEvent != null) {
                return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete an event and its associated image
     */
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Map<String, Boolean>> deleteEvent(@PathVariable String eventId) {
        boolean deleted = eventService.deleteEvent(eventId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", deleted);

        if (deleted) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}