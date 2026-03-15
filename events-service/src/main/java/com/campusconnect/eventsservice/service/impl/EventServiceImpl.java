package com.campusconnect.eventsservice.service.impl;

import com.campusconnect.eventsservice.dto.EventDTO;
import com.campusconnect.eventsservice.entity.Event;
import com.campusconnect.eventsservice.repository.EventRepository;
import com.campusconnect.eventsservice.service.IEventService;
import com.campusconnect.eventsservice.service.S3StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements IEventService {

    private final EventRepository eventRepository;
    private final S3StorageService s3StorageService;

    public EventServiceImpl(EventRepository eventRepository, S3StorageService s3StorageService) {
        this.eventRepository = eventRepository;
        this.s3StorageService = s3StorageService;
    }

    @Override
    public EventDTO createEvent(EventDTO eventDTO, MultipartFile imageFile) throws IOException {
        // Upload image to AWS S3 if provided
        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageUrl = s3StorageService.uploadImage(imageFile);
        }

        // Create and save event
        Event event = new Event();
        event.setEventId(UUID.randomUUID().toString());
        event.setTitle(eventDTO.getTitle());
        event.setContent(eventDTO.getContent());
        event.setContentImageUrl(imageUrl);
        event.setPostedBy(eventDTO.getPostedBy());

        Event savedEvent = eventRepository.save(event);

        // Convert back to DTO
        EventDTO savedEventDTO = convertToDTO(savedEvent);
        return savedEventDTO;
    }

    @Override
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EventDTO getEventById(String eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        return eventOptional.map(this::convertToDTO).orElse(null);
    }

    @Override
    public EventDTO updateEvent(String eventId, EventDTO eventDTO, MultipartFile imageFile) throws IOException {
        Optional<Event> eventOptional = eventRepository.findById(eventId);

        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.setTitle(eventDTO.getTitle());
            event.setContent(eventDTO.getContent());
            event.setPostedBy(eventDTO.getPostedBy());

            // Update image if a new one is provided
            if (imageFile != null && !imageFile.isEmpty()) {
                // Delete old image if exists
                if (event.getContentImageUrl() != null) {
                    s3StorageService.deleteImage(event.getContentImageUrl());
                }

                // Upload new image
                String newImageUrl = s3StorageService.uploadImage(imageFile);
                event.setContentImageUrl(newImageUrl);
            }

            Event updatedEvent = eventRepository.save(event);
            return convertToDTO(updatedEvent);
        }

        return null;
    }

    @Override
    public boolean deleteEvent(String eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);

        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();

            // Delete image from S3 if exists
            if (event.getContentImageUrl() != null) {
                s3StorageService.deleteImage(event.getContentImageUrl());
            }

            eventRepository.deleteById(eventId);
            return true;
        }

        return false;
    }

    // Helper method to convert Entity to DTO
    private EventDTO convertToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setTitle(event.getTitle());
        dto.setContent(event.getContent());
        dto.setContentImageUrl(event.getContentImageUrl());
        dto.setPostedBy(event.getPostedBy());
        return dto;
    }
}