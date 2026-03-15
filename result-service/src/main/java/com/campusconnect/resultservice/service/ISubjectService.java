package com.campusconnect.resultservice.service;

import com.campusconnect.resultservice.dto.SubjectDTO;

import java.util.List;
import java.util.Optional;

public interface ISubjectService {


    List<SubjectDTO> getAllSubjects();

    Optional<SubjectDTO> getSubjectById(Integer subjectId);

    List<SubjectDTO> getSubjectsByResultId(Integer resultId);

    SubjectDTO saveSubject(SubjectDTO subjectDTO);

    void deleteSubject(Integer subjectId);

}
