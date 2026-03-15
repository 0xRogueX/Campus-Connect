package com.campusconnect.schedulingservice.service.impl;


import com.campusconnect.schedulingservice.dto.ScheduleDto;
import com.campusconnect.schedulingservice.entity.Schedule;
import com.campusconnect.schedulingservice.exception.ResourceNotFoundException;
import com.campusconnect.schedulingservice.exception.UnauthorizedAccessException;
import com.campusconnect.schedulingservice.mapper.ScheduleMapper;
import com.campusconnect.schedulingservice.repository.ScheduleRepository;
import com.campusconnect.schedulingservice.service.IScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
public class ScheduleServiceImpl implements IScheduleService {

    private  final ScheduleRepository scheduleRepository;
    private final JWTService jwtService;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, JWTService jwtService) {
        this.scheduleRepository = scheduleRepository;
        this.jwtService = jwtService;
    }


    @Override
    public ScheduleDto createSchedule(ScheduleDto scheduleDto , String token) {

        if(jwtService.isAdminOrFaculty(token)){
            throw new UnauthorizedAccessException("Only admin can perform this action.");

        }

        scheduleDto.setFaculty(scheduleDto.getFaculty().toUpperCase());
        scheduleDto.setBranch(scheduleDto.getBranch().toUpperCase());
        scheduleDto.setDivision(scheduleDto.getDivision().toUpperCase());
        scheduleDto.setDayOfWeek(scheduleDto.getDayOfWeek().toUpperCase());
        scheduleDto.setSubject(scheduleDto.getSubject().toUpperCase());
        scheduleDto.setStartTime(scheduleDto.getStartTime().toUpperCase());
        scheduleDto.setEndTime(scheduleDto.getEndTime().toUpperCase());
        scheduleDto.setRoom(scheduleDto.getRoom().toUpperCase());
        boolean conflict = scheduleRepository.isScheduleConflict(
                scheduleDto.getFaculty(),
                scheduleDto.getBranch(),
                scheduleDto.getSemester(),
                scheduleDto.getDivision(),
                scheduleDto.getDayOfWeek(),
                scheduleDto.getStartTime(),
                scheduleDto.getRoom()
        );
        boolean conflict1 = scheduleRepository.isScheduleConflict1(
                scheduleDto.getBranch(),
                scheduleDto.getSemester(),
                scheduleDto.getDivision(),
                scheduleDto.getDayOfWeek(),
                scheduleDto.getSubject(),
                scheduleDto.getStartTime()
        );
        boolean conflict2 = scheduleRepository.isScheduleConflict2(
                scheduleDto.getFaculty(),
                scheduleDto.getBranch(),
                scheduleDto.getSemester(),
                scheduleDto.getDivision(),
                scheduleDto.getDayOfWeek(),
                scheduleDto.getStartTime()
        );
        boolean conflict3 = scheduleRepository.isScheduleConflict3(
                scheduleDto.getBranch(),
                scheduleDto.getSemester(),
                scheduleDto.getDivision(),
                scheduleDto.getDayOfWeek(),
                scheduleDto.getStartTime()
        );

        if (conflict) {
            throw new IllegalArgumentException("Schedule conflict detected! Another course is already scheduled at the same day, time, and location.");
        }
        if (conflict1) {
            throw new IllegalArgumentException("Schedule conflict detected! Another course is already scheduled at  faculty.");
        }
        if (conflict2) {
            throw new IllegalArgumentException("Schedule conflict detected! Another course is already scheduled at  location.");
        }
        if (conflict3) {
            throw new IllegalArgumentException("Schedule conflict detected! Another course is already scheduled at  location,faculty.");
        }

        Schedule schedule = ScheduleMapper.toEntity(scheduleDto);
        Schedule savedSchedule = scheduleRepository.saveAndFlush(schedule);

        return ScheduleMapper.toDto(savedSchedule);   }


    @Transactional
    @Override
    public List<ScheduleDto> saveAll(List<ScheduleDto> scheduleDtos, String token) {
        if(jwtService.isAdminOrFaculty(token)){
            throw new UnauthorizedAccessException("Only admin can perform this action.");

        }


        AtomicInteger counter = new AtomicInteger();
        List<Schedule> savedSchedules = scheduleDtos.stream()
                .map(scheduleDto -> {

                    Schedule schedule = ScheduleMapper.toEntity(scheduleDto);
                    scheduleDto = ScheduleMapper.toDto(schedule);

                    boolean conflict = scheduleRepository.isScheduleConflict(
                            scheduleDto.getFaculty(),
                            scheduleDto.getBranch(),
                            scheduleDto.getSemester(),
                            scheduleDto.getDivision(),
                            scheduleDto.getDayOfWeek(),
                            scheduleDto.getStartTime(),
                            scheduleDto.getRoom()
                    );
                    boolean conflict1 = scheduleRepository.isScheduleConflict1(
                            scheduleDto.getBranch(),
                            scheduleDto.getSemester(),
                            scheduleDto.getDivision(),
                            scheduleDto.getDayOfWeek(),
                            scheduleDto.getSubject(),
                            scheduleDto.getStartTime()
                    );
                    boolean conflict2 = scheduleRepository.isScheduleConflict2(
                            scheduleDto.getFaculty(),
                            scheduleDto.getBranch(),
                            scheduleDto.getSemester(),
                            scheduleDto.getDivision(),
                            scheduleDto.getDayOfWeek(),
                            scheduleDto.getStartTime()
                    );
                    boolean conflict3 = scheduleRepository.isScheduleConflict3(
                            scheduleDto.getBranch(),
                            scheduleDto.getSemester(),
                            scheduleDto.getDivision(),
                            scheduleDto.getDayOfWeek(),
                            scheduleDto.getStartTime()
                    );

                    if (conflict) {
                        throw new IllegalArgumentException("Schedule conflict detected! Another course is already scheduled at the same day, time, and location.");
                    }
                    if (conflict1) {
                        throw new IllegalArgumentException("Schedule conflict detected! Another course is already scheduled at  faculty.");
                    }
                    if (conflict2) {
                        throw new IllegalArgumentException("Schedule conflict detected! Another course is already scheduled at  location.");
                    }
                    if (conflict3) {
                        throw new IllegalArgumentException("Schedule conflict detected! Another course is already scheduled at  location,faculty.");
                    }
                    schedule.setLectureId(scheduleDto.getSemester()+scheduleDto.getDivision()+String.valueOf((counter.get()+1)));
                    counter.getAndIncrement();
                    return scheduleRepository.saveAndFlush(schedule);
                })
                .toList();
        return savedSchedules.stream()
                .map(schedule -> ScheduleMapper.toDto(schedule))
                .collect(Collectors.toList());

    }

    @Override
    public List<ScheduleDto> findScheduleBySemAndDiv(Integer semester, String division) {
        List<Schedule> schedules = scheduleRepository.findScheduleBySemesterAndDivision(semester,division.toUpperCase());
        //    List<Schedule> schedules = scheduleRepository.findScheduleByDivision(division.toUpperCase());

        if(schedules.isEmpty()){
            throw new ResourceNotFoundException("schedules not found");
        }
        return schedules.stream()
                .map(schedule -> ScheduleMapper.toDto(schedule))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDto> findScheduleBySemAndDivAndDow(Integer semester, String division, String DayOfWeek) {
        List<Schedule> schedules = scheduleRepository.findScheduleBySemesterAndDivisionAndDayOfWeek(semester,division.toUpperCase(),DayOfWeek.toUpperCase());

        if(schedules.isEmpty()){
            throw new ResourceNotFoundException("schedules not found");
        }
        return schedules.stream()
                .map(schedule -> ScheduleMapper.toDto(schedule))
                .collect(Collectors.toList());    }

    @Override
    public List<ScheduleDto> updateSchedule(ScheduleDto scheduleDto, Integer semester, String division, Integer index, String token) {
        if(jwtService.isAdminOrFaculty(token)){
            throw new UnauthorizedAccessException("Only admin can perform this action.");

        }

        List<Schedule> schedules = scheduleRepository.findScheduleBySemesterAndDivision(semester, division.toUpperCase());
        if (schedules == null || schedules.isEmpty()) {
            throw new ResourceNotFoundException("No schedules found for the provided semester and division.");
        }

        Schedule schedule = scheduleRepository.findById(index).get();
        schedule.setId(schedule.getId());
        schedule.setBranch(scheduleDto.getBranch());
        schedule.setDivision(scheduleDto.getDivision());
        scheduleDto.setFaculty(scheduleDto.getFaculty());
        schedule.setSubject(scheduleDto.getSubject().toUpperCase());
        schedule.setFaculty(scheduleDto.getFaculty().toUpperCase());
        schedule.setRoom(scheduleDto.getRoom().toUpperCase());
        schedule.setStartTime(scheduleDto.getStartTime().toUpperCase());
        schedule.setEndTime(scheduleDto.getEndTime().toUpperCase());
        scheduleRepository.save(schedule);

        schedules = scheduleRepository.findScheduleBySemesterAndDivision(semester, division.toUpperCase());


        return schedules.stream().map(s -> ScheduleMapper.toDto(s)).collect(Collectors.toList());

    }


}
