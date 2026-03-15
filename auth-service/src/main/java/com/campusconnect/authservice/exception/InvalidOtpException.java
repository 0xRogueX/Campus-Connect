package com.campusconnect.authservice.exception;

public class InvalidOtpException extends RuntimeException {
    public InvalidOtpException(String msg) { super(msg); }
}