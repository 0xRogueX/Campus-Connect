package com.campusconnect.schedulingservice.mapper;


import com.campusconnect.schedulingservice.dto.ScheduleDto;
import com.campusconnect.schedulingservice.entity.Schedule;

public class ScheduleMapper {

    public static Schedule toEntity(ScheduleDto dto) {
        if (dto == null) {
            return null;
        }

        Schedule schedule = new Schedule();
        schedule.setLectureId(dto.getLectureId());
        schedule.setBranch(dto.getBranch().toUpperCase().trim());
        schedule.setSemester(dto.getSemester());
        schedule.setDivision(dto.getDivision().toUpperCase().trim());
        schedule.setDayOfWeek(dto.getDayOfWeek().toUpperCase().trim());
schedule.setStartTime(dto.getStartTime().toUpperCase().trim());
schedule.setEndTime(dto.getEndTime().toUpperCase().trim());
schedule.setFaculty(dto.getFaculty().toUpperCase().toUpperCase());
schedule.setSubject(dto.getSubject().toUpperCase().trim());
schedule.setRoom(dto.getRoom().toUpperCase().trim());



        return schedule;

    }

    public static ScheduleDto toDto(Schedule entity) {
        if (entity == null) {
            return null;
        }


        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setId(entity.getId());
        scheduleDto.setLectureId(entity.getLectureId());
        scheduleDto.setBranch(entity.getBranch().toUpperCase().trim());
        scheduleDto.setSemester(entity.getSemester());
        scheduleDto.setDivision(entity.getDivision().toUpperCase().trim());
        scheduleDto.setDayOfWeek(entity.getDayOfWeek().toUpperCase().trim());
        scheduleDto.setStartTime(entity.getStartTime().toUpperCase().trim());
        scheduleDto.setEndTime(entity.getEndTime().toUpperCase().trim());
        scheduleDto.setFaculty(entity.getFaculty().toUpperCase().trim());
        scheduleDto.setSubject(entity.getSubject().toUpperCase().trim());
        scheduleDto.setRoom(entity.getRoom().toUpperCase().trim());
        return scheduleDto;
    }

}
