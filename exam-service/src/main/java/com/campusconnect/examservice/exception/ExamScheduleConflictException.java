package com.campusconnect.examservice.exception;

public class ExamScheduleConflictException extends RuntimeException {
    public ExamScheduleConflictException(String message) {
        super(message);
    }
}
