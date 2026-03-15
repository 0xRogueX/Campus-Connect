package com.campusconnect.examservice.service;

import com.campusconnect.examservice.dto.ExamDTO;
import com.campusconnect.examservice.dto.ExamSubjectDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IExamService {
    ExamDTO createExam(ExamDTO examDTO);
    ExamDTO getExamById(Long id);
    List<ExamDTO> getAllExams();
    List<ExamDTO> getExamsBySemester(String semester);
    List<ExamDTO> getExamsByBranch(String branch);
    ExamDTO updateExam(Long id, ExamDTO examDTO);
    void deleteExam(Long id);
    ExamSubjectDTO addSubjectToExam(Long examId, ExamSubjectDTO examSubjectDTO);
    void removeSubjectFromExam(Long examId, Long subjectId);
    List<ExamSubjectDTO> getAllSubjectsForExam(Long examId);


}
