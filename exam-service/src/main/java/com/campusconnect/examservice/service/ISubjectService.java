package com.campusconnect.examservice.service;

import com.campusconnect.examservice.dto.ExamSubjectDTO;
import com.campusconnect.examservice.dto.SubjectDTO;

import java.util.List;

public interface ISubjectService {

    SubjectDTO createSubject(SubjectDTO subjectDTO);
    SubjectDTO getSubjectById(Long id);
    SubjectDTO getSubjectByCode(String code);
    List<SubjectDTO> getAllSubjects();
    List<SubjectDTO> createSubjects(List<SubjectDTO> subjectDTOs);


    SubjectDTO updateSubject(Long id, SubjectDTO subjectDTO);
    void deleteSubject(Long id);
    List<ExamSubjectDTO> getExamsForSubject(Long subjectId);
}
