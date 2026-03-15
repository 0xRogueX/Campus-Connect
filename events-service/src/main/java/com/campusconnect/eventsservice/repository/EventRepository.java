package com.campusconnect.eventsservice.repository;


import com.campusconnect.eventsservice.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EventRepository extends MongoRepository<Event,String> {

}
