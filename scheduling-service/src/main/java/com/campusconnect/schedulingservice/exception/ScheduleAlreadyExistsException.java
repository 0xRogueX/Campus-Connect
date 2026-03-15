package com.campusconnect.schedulingservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ScheduleAlreadyExistsException extends  RuntimeException{
    public ScheduleAlreadyExistsException( String message){
        super(message);

    }
}
