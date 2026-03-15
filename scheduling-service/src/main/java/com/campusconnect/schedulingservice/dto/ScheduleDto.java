package com.campusconnect.schedulingservice.dto;

import lombok.Data;


@Data
public class ScheduleDto {

    private Integer id;
    private String lectureId;
    private String branch;
    private Integer semester;
    private String  division;
    private String dayOfWeek;
    private String faculty;
    private String subject;
    private String startTime;
    private String endTime;
    private String room;

}
