package com.campusconnect.examservice.mapper;

import com.campusconnect.examservice.dto.ExamSubjectDTO;
import com.campusconnect.examservice.entity.ExamSubject;
import com.campusconnect.examservice.entity.Exam;
import com.campusconnect.examservice.entity.Subject;

import java.util.List;
import java.util.stream.Collectors;

public class ExamSubjectMapper {

    public static ExamSubjectDTO toDTO(ExamSubject examSubject) {
        if (examSubject == null) {
            return null;
        }
        return new ExamSubjectDTO(
                examSubject.getId(),
                examSubject.getExam() != null ? examSubject.getExam().getId() : null,
                examSubject.getSubject() != null ? examSubject.getSubject().getId() : null,
                examSubject.getSubject() != null ? examSubject.getSubject().getSubjectName() : null,
                examSubject.getSubject() != null ? examSubject.getSubject().getSubjectCode() : null,
                examSubject.getSubject() != null ? examSubject.getSubject().getCredits() : null,
                examSubject.getExamDate(),
                examSubject.getStartTime(),
                examSubject.getEndTime()
        );
    }

    public static ExamSubject toEntity(ExamSubjectDTO dto, Exam exam, Subject subject) {
        if (dto == null) {
            return null;
        }
        ExamSubject examSubject = new ExamSubject();
        examSubject.setId(dto.getId());
        examSubject.setExam(exam);
        examSubject.setSubject(subject);
        examSubject.setExamDate(dto.getExamDate());
        examSubject.setStartTime(dto.getStartTime());
        examSubject.setEndTime(dto.getEndTime());
        return examSubject;
    }

    public static List<ExamSubjectDTO> toDTOList(List<ExamSubject> examSubjects) {
        return examSubjects.stream()
                .map(ExamSubjectMapper::toDTO)
                .collect(Collectors.toList());
    }
}
