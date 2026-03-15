package com.campusconnect.resultservice.constant;

import java.util.Map;

public class SubjectGrades {
    private SubjectGrades(){}
    public static final Map<String,Integer> subjectGrades = Map.of(
            "AA",10,
            "AB",9,
            "BB",8,
            "BC",7,
            "CC",6,
            "CD",5,
            "DD",4,
            "DE",3,
            "FF",0
    );
}
