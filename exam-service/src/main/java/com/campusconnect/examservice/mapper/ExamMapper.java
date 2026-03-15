package com.campusconnect.examservice.mapper;

import com.campusconnect.examservice.dto.ExamDTO;
import com.campusconnect.examservice.dto.ExamSubjectDTO;
import com.campusconnect.examservice.entity.Exam;
import com.campusconnect.examservice.entity.ExamSubject;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ExamMapper {


    public static ExamDTO toDTO(Exam exam) {
        if (exam == null) return null;

        ExamDTO dto = new ExamDTO();
        dto.setId(exam.getId());
        dto.setExamName(exam.getExamName());
        dto.setExamStart(exam.getExamStart());
        dto.setExamEnd(exam.getExamEnd());
        dto.setExamDuration(exam.getExamDuration());
        dto.setSemester(exam.getSemester());
        dto.setBranch(exam.getBranch());

        Set<ExamSubjectDTO> subjects = exam.getSubjects().stream()
                .map(ExamMapper::toExamSubjectDTO)
                .collect(Collectors.toSet());

        dto.setSubjects(subjects);

        return dto;
    }

    private static ExamSubjectDTO toExamSubjectDTO(ExamSubject examSubject) {
        if (examSubject == null) return null;

        ExamSubjectDTO dto = new ExamSubjectDTO();
        dto.setId(examSubject.getId());
        dto.setExamDate(examSubject.getExamDate());
        dto.setStartTime(examSubject.getStartTime());
        dto.setEndTime(examSubject.getEndTime());
        dto.setCredits(examSubject.getSubject() != null ? examSubject.getSubject().getCredits() : null);
        dto.setSubjectName(examSubject.getSubject() != null ? examSubject.getSubject().getSubjectName() : null);
        dto.setSubjectCode(examSubject.getSubject() != null ? examSubject.getSubject().getSubjectCode() : null);
        dto.setSubjectId(examSubject.getSubject() != null ? examSubject.getSubject().getId() : null);
        dto.setExamId(examSubject.getExam() != null ? examSubject.getExam().getId() : null);

        return dto;
    }
    public static Exam toEntity(ExamDTO dto) {
        if (dto == null) {
            return null;
        }
        Exam exam = new Exam();
        exam.setId(dto.getId());
        exam.setExamName(dto.getExamName());
        exam.setExamStart(dto.getExamStart());
        exam.setExamEnd(dto.getExamEnd());
        exam.setExamDuration(dto.getExamDuration());
        exam.setSemester(dto.getSemester());
        exam.setBranch(dto.getBranch());
        // Subjects are handled separately usually (since you need Subject/Exam references)
        return exam;
    }

    public static List<ExamDTO> toDTOList(List<Exam> exams) {
        return exams.stream()
                .map(ExamMapper::toDTO)
                .collect(Collectors.toList());
    }
}
