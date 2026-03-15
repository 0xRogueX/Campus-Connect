package com.campusconnect.examservice.mapper;

import com.campusconnect.examservice.dto.SubjectDTO;
import com.campusconnect.examservice.entity.Subject;

import java.util.List;
import java.util.stream.Collectors;

public class SubjectMapper {

    public static SubjectDTO toDTO(Subject subject) {
        if (subject == null) {
            return null;
        }
        return new SubjectDTO(
                subject.getId(),
                subject.getSubjectName(),
                subject.getSubjectCode(),
                subject.getCredits()
        );
    }

    public static Subject toEntity(SubjectDTO dto) {
        if (dto == null) {
            return null;
        }
        Subject subject = new Subject();
        subject.setId(dto.getId());
        subject.setSubjectName(dto.getSubjectName());
        subject.setSubjectCode(dto.getSubjectCode());
        subject.setCredits(dto.getCredits());
        return subject;
    }

    public static List<SubjectDTO> toDTOList(List<Subject> subjects) {
        return subjects.stream()
                .map(SubjectMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Subject> toEntityList(List<SubjectDTO> subjectDTOs) {
        return subjectDTOs.stream()
                .map(SubjectMapper::toEntity)
                .collect(Collectors.toList());
    }
}
