package com.campusconnect.schedulingservice.service;



import com.campusconnect.schedulingservice.dto.ScheduleDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IScheduleService {


    ScheduleDto createSchedule(ScheduleDto scheduleDto , String token);

    List<ScheduleDto> saveAll(List<ScheduleDto> scheduleDtos, String token);

    List<ScheduleDto> findScheduleBySemAndDiv(Integer semester, String division);

    List<ScheduleDto> findScheduleBySemAndDivAndDow(Integer semester, String division, String DayOfWeek);

    List<ScheduleDto> updateSchedule(ScheduleDto scheduleDto, Integer semester, String division, Integer index , String token);

}