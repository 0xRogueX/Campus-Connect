/*
package com.campusconnect.schedulingservice.controller;


import com.campusconnect.schedulingservice.dto.ScheduleDto;
import com.campusconnect.schedulingservice.service.IScheduleService;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ScheduleControllerGQL {

    private final IScheduleService scheduleService;

    public ScheduleControllerGQL(IScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @QueryMapping
    public List<ScheduleDto> getScheduleBySemAndDiv(@Argument Integer semester, @Argument String division) {
        return scheduleService.findScheduleBySemAndDiv(semester, division.toUpperCase());
    }
}
*/
