package com.campusconnect.schedulingservice.repository;


import com.campusconnect.schedulingservice.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableJpaRepositories
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findScheduleBySemesterAndDivision(Integer semester, String division);
    List<Schedule> findScheduleByDivision( String division);


    List<Schedule> findScheduleBySemesterAndDivisionAndDayOfWeek(Integer semester, String division, String dayOfWeek);


    @Query("SELECT COUNT(s) > 0 FROM Schedule s WHERE s.faculty = :faculty AND s.branch = :branch AND s.semester = :semester AND s.division = :division AND s.dayOfWeek = :dayOfWeek AND  s.startTime = :startTime AND s.room = :room")
    boolean isScheduleConflict(@Param("faculty") String faculty,
                               @Param("branch") String branch,
                               @Param("semester") Integer semester,
                               @Param("division") String division,
                               @Param("dayOfWeek") String dayOfWeek,
                               @Param("startTime") String startTime,
                               @Param("room") String room);

    @Query("SELECT COUNT(s) > 0 FROM Schedule s WHERE s.branch = :branch AND s.semester = :semester AND s.division = :division AND s.dayOfWeek = :dayOfWeek AND s.subject = :subject AND s.startTime = :startTime ")
    boolean isScheduleConflict1(
            @Param("branch") String branch,
            @Param("semester") Integer semester,
            @Param("division") String division,
            @Param("dayOfWeek") String dayOfWeek,
            @Param("subject") String subject,
            @Param("startTime") String startTime);

    @Query("SELECT COUNT(s) > 0 FROM Schedule s WHERE s.faculty = :faculty AND s.branch = :branch AND s.semester = :semester AND s.division = :division AND s.dayOfWeek = :dayOfWeek AND s.startTime = :startTime ")
    boolean isScheduleConflict2(
            @Param("faculty") String faculty,
            @Param("branch") String branch,
            @Param("semester") Integer semester,
            @Param("division") String division,
            @Param("dayOfWeek") String dayOfWeek,
            @Param("startTime") String startTime);

    @Query("SELECT COUNT(s) > 0 FROM Schedule s WHERE  s.branch = :branch AND s.semester = :semester AND s.division = :division AND s.dayOfWeek = :dayOfWeek AND s.startTime = :startTime ")
    boolean isScheduleConflict3(
            @Param("branch") String branch,
            @Param("semester") Integer semester,
            @Param("division") String division,
            @Param("dayOfWeek") String dayOfWeek,
            @Param("startTime") String startTime);

}
