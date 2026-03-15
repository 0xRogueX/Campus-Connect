package com.campusconnect.authservice.constant;

// Resource type
    public class ResponseConstants {

        // Resource type
        public static final String RESOURCE = "resource";

        // Success responses
        public static final String STATUS_201 = "201";
        public static final String MESSAGE_201 = "Created successfully";

        public static final String STATUS_200 = "200";
        public static final String MESSAGE_200 = "Retrieved successfully";
        public static final String MESSAGE_200_UPDATE = "Updated successfully";
        public static final String MESSAGE_200_DELETE = "Deleted successfully";

        // Client error responses
        public static final String STATUS_400 = "400";
        public static final String MESSAGE_400 = "Bad request - Invalid input";

        public static final String STATUS_401 = "401";
        public static final String MESSAGE_401 = "Unauthorized - Authentication required";

        public static final String STATUS_403 = "403";
        public static final String MESSAGE_403 = "Forbidden - Access denied";

        public static final String STATUS_404 = "404";
        public static final String MESSAGE_404 = "Not found";

        public static final String STATUS_405 = "405";
        public static final String MESSAGE_405 = "Method not allowed for this operation";

        public static final String STATUS_409 = "409";
        public static final String MESSAGE_409 = "Already exists";

        public static final String STATUS_415 = "415";
        public static final String MESSAGE_415 = "Unsupported media type";

        public static final String STATUS_422 = "422";
        public static final String MESSAGE_422 = "Unprocessable entity - Validation failed";

        public static final String STATUS_417 = "417";
        public static final String MESSAGE_417_UPDATE = "Update failed, please retry";
        public static final String MESSAGE_417_DELETE = "Delete failed, please retry";

        public static final String STATUS_429 = "429";
        public static final String MESSAGE_429 = "Too many requests - Rate limit exceeded";

        // Server error responses
        public static final String STATUS_500 = "500";
        public static final String MESSAGE_500 = "Internal server error";

        public static final String STATUS_504 = "504";
        public static final String MESSAGE_504 = "Gateway timeout";

    }



