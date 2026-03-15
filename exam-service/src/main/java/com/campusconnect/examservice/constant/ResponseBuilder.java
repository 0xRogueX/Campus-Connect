package com.campusconnect.examservice.constant;

import com.campusconnect.examservice.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

    private ResponseBuilder() {} // Private constructor to prevent instantiation

    public static ResponseEntity<ResponseDTO> success(String message, Object data) {
        return build(ResponseConstants.STATUS_200, message, true, data, HttpStatus.OK);
    }

    public static ResponseEntity<ResponseDTO> created(String message, Object data) {
        return build(ResponseConstants.STATUS_201, message, true, data, HttpStatus.CREATED);
    }

    public static ResponseEntity<ResponseDTO> error(String statusCode, String message, HttpStatus httpStatus) {
        return build(statusCode, message, false, null, httpStatus);
    }

    private static ResponseEntity<ResponseDTO> build(
            String statusCode,
            String message,
            boolean status,
            Object data,
            HttpStatus httpStatus
    ) {
        ResponseDTO response = ResponseDTO.builder()
                .statusCode(statusCode)
                .statusMsg(message)
                .status(status)
                .data(data)
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }
}
