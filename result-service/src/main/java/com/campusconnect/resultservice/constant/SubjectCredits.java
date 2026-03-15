package com.campusconnect.resultservice.constant;

import java.util.Map;

public class SubjectCredits {

    private SubjectCredits() {}
    public static final Map<String, Integer> subjectCredits = Map.of(
            "JAVA", 3,
            "WP", 3,
            "TOC", 3,
            "IOT", 3,
            "IPDC", 2,
            "DE", 2,
            "MPI", 1,
            "OTHER", 1
    );
}
