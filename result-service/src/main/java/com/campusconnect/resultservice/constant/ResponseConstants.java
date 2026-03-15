
package com.campusconnect.resultservice.constant;

public class ResponseConstants {
    private ResponseConstants() {}

    // Resource types
    public static final String RESULT = "result";
    public static final String STUDENT = "student";
    public static final String SUBJECT = "subject";

    // Success responses
    public static final String STATUS_201 = "201";
    public static final String MESSAGE_201_RESULT = "Result created successfully";
    public static final String MESSAGE_201_STUDENT = "Student created successfully";
    public static final String MESSAGE_201_SUBJECT = "Subject created successfully";

    public static final String STATUS_200 = "200";
    public static final String MESSAGE_200_RESULT = "Result retrieved successfully";
    public static final String MESSAGE_200_STUDENT = "Student retrieved successfully";
    public static final String MESSAGE_200_SUBJECT = "Subject retrieved successfully";
    public static final String MESSAGE_200_UPDATE = "Resource updated successfully";
    public static final String MESSAGE_200_DELETE = "Resource deleted successfully";

    // Client error responses
    public static final String STATUS_400 = "400";
    public static final String MESSAGE_400 = "Bad request - Invalid input parameters";

    public static final String STATUS_401 = "401";
    public static final String MESSAGE_401 = "Unauthorized - Authentication required";

    public static final String STATUS_403 = "403";
    public static final String MESSAGE_403 = "Forbidden - Insufficient permissions";

    public static final String STATUS_404 = "404";
    public static final String MESSAGE_404_RESULT = "Result not found";
    public static final String MESSAGE_404_STUDENT = "Student not found";
    public static final String MESSAGE_404_SUBJECT = "Subject not found";

    public static final String STATUS_405 = "405";
    public static final String MESSAGE_405 = "Method not allowed";

    public static final String STATUS_409 = "409";
    public static final String MESSAGE_409_RESULT = "Result already exists";
    public static final String MESSAGE_409_STUDENT = "Student already exists";

    public static final String STATUS_415 = "415";
    public static final String MESSAGE_415 = "Unsupported media type";

    public static final String STATUS_422 = "422";
    public static final String MESSAGE_422 = "Unprocessable entity - Validation failed";

    public static final String STATUS_417 = "417";
    public static final String MESSAGE_417_UPDATE = "Update operation failed, please try again or contact support";
    public static final String MESSAGE_417_DELETE = "Delete operation failed, please try again or contact support";

    public static final String STATUS_429 = "429";
    public static final String MESSAGE_429 = "Too many requests - Rate limit exceeded";

    // Server error responses
    public static final String STATUS_500 = "500";
    public static final String MESSAGE_500 = "Internal server error";

    public static final String STATUS_504 = "504";
    public static final String MESSAGE_504 = "Gateway timeout";

    // Result-specific messages
    public static final String RESULT_PUBLISHED = "Result published successfully";
    public static final String RESULT_UPDATED = "Result updated successfully";
    public static final String RESULT_DELETED = "Result deleted successfully";
    public static final String GRADE_CALCULATION_ERROR = "Error while calculating grades";

    // Export-specific messages
    public static final String EXPORT_SUCCESS = "Result exported successfully";
    public static final String EXPORT_ERROR = "Error while exporting result";
}