package com.campusconnect.schedulingservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "schedule")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule extends BaseEntity {



        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
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
