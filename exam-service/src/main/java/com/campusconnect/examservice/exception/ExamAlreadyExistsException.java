package com.campusconnect.examservice.exception;

public class ExamAlreadyExistsException extends RuntimeException {
    public ExamAlreadyExistsException(String message) {
        super(message);
    }
}
