package com.campusconnect.schedulingservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends  RuntimeException{
    public ResourceNotFoundException(String message){
        super(String.format("%s not found with the given input data %s : %s",message));

    }
}
