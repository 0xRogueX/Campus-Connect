package com.campusconnect.authservice.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String msg) { super(msg); }
}