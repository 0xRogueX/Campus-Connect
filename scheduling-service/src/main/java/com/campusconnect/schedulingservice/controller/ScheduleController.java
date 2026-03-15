package com.campusconnect.schedulingservice.controller;


import com.campusconnect.schedulingservice.constants.ResponseConstants;
import com.campusconnect.schedulingservice.dto.ResponseDto;
import com.campusconnect.schedulingservice.dto.ScheduleDto;
import com.campusconnect.schedulingservice.service.IScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/schedule")
@AllArgsConstructor
public class ScheduleController {

  private final IScheduleService scheduleService;


  @GetMapping("/healthcheck")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto> createSchedule(@RequestBody ScheduleDto scheduleDto, @RequestHeader("Authorization") String token) {
         scheduleService.createSchedule(scheduleDto,token);
         return ResponseEntity.status(201).body(new ResponseDto(ResponseConstants.STATUS_201, ResponseConstants.MESSAGE_201));

    }
    @PostMapping("/save/all")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto> saveAllSchedule(@RequestBody List<ScheduleDto> scheduleDto ,@RequestHeader("Authorization") String token) {
         scheduleService.saveAll(scheduleDto ,token);
        return ResponseEntity.status(201).body(new ResponseDto(ResponseConstants.STATUS_201, ResponseConstants.MESSAGE_201));

    }

    @GetMapping("/get-by-sem-div")
    @ResponseStatus(HttpStatus.OK)
    public Map<Integer, Map<String, List<ScheduleDto>>> getScheduleBySemAndDiv(@RequestParam Integer semester, @RequestParam String division) {
        List<ScheduleDto> schedules =  scheduleService.findScheduleBySemAndDiv(semester,division.toUpperCase());


        List<ScheduleDto> monday =   scheduleService.findScheduleBySemAndDivAndDow(semester,division.toUpperCase(),"monday");
        List<ScheduleDto> tuesday =  scheduleService.findScheduleBySemAndDivAndDow(semester,division.toUpperCase(),"tuesday");

        List<ScheduleDto> wednesday =  scheduleService.findScheduleBySemAndDivAndDow(semester,division.toUpperCase(),"wednesday");

        List<ScheduleDto> thursday =  scheduleService.findScheduleBySemAndDivAndDow(semester,division.toUpperCase(),"thursday");

        List<ScheduleDto> friday = scheduleService.findScheduleBySemAndDivAndDow(semester,division.toUpperCase(),"friday");
        List<ScheduleDto> saturday = scheduleService.findScheduleBySemAndDivAndDow(semester,division.toUpperCase(),"saturday");

        Map<Integer, Map<String,List<ScheduleDto>>> map = new HashMap<>();
        map.put(1, Map.of("monday", monday));
        map.put(2, Map.of("tuesday", tuesday));
        map.put(3, Map.of("wednesday", wednesday));
        map.put(4, Map.of("thursday", thursday));
        map.put(5, Map.of("friday", friday));
        map.put(6, Map.of("saturday", saturday));



        return map ;


    }
    @GetMapping("/get-by-sem-div-dow")
    @ResponseStatus(HttpStatus.OK)
    public List<ScheduleDto> getScheduleBySemAndDivAndDow(@RequestParam Integer semester,@RequestParam String division,@RequestParam String dayOfWeek) {

        return  scheduleService.findScheduleBySemAndDivAndDow(semester,division.toUpperCase(),dayOfWeek.toLowerCase());


    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public List<ScheduleDto> updateSchedule(@RequestBody ScheduleDto scheduleDto,@RequestParam Integer semester,@RequestParam String division,@RequestParam Integer index ,@RequestHeader("Authorization") String token) {

        return  scheduleService.updateSchedule(scheduleDto,semester,division.toUpperCase(),index,token) ;


    }

}
