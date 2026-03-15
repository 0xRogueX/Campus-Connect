package com.campusconnect.examservice.controller;

import com.campusconnect.examservice.constant.ResponseBuilder;
import com.campusconnect.examservice.constant.ResponseConstants;
import com.campusconnect.examservice.dto.ExamSubjectDTO;
import com.campusconnect.examservice.dto.ResponseDTO;
import com.campusconnect.examservice.dto.SubjectDTO;
import com.campusconnect.examservice.service.ISubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams/subjects")
public class SubjectController {

    private final ISubjectService subjectService;

    public SubjectController(ISubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createSubject(@RequestBody SubjectDTO subjectDTO) {
        SubjectDTO createdSubject = subjectService.createSubject(subjectDTO);
        return  ResponseBuilder.success(ResponseConstants.MESSAGE_201, createdSubject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getSubjectById(@PathVariable Long id) {
        SubjectDTO subject = subjectService.getSubjectById(id);
        return  ResponseBuilder.success(ResponseConstants.MESSAGE_200, subject);
    }
    @PostMapping("/batch")
    public ResponseEntity<ResponseDTO> createSubjects(@RequestBody List<SubjectDTO> subjectDTOs) {
        List<SubjectDTO> createdSubjects = subjectService.createSubjects(subjectDTOs);
        return  ResponseBuilder.success(ResponseConstants.MESSAGE_201, createdSubjects);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ResponseDTO> getSubjectByCode(@PathVariable String code) {
        SubjectDTO subject = subjectService.getSubjectByCode(code);
        return  ResponseBuilder.success(ResponseConstants.MESSAGE_200, subject);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllSubjects() {
        List<SubjectDTO> subjects = subjectService.getAllSubjects();
        return  ResponseBuilder.success(ResponseConstants.MESSAGE_200, subjects);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateSubject(@PathVariable Long id, @RequestBody SubjectDTO subjectDTO) {
        SubjectDTO updatedSubject = subjectService.updateSubject(id, subjectDTO);
        return  ResponseBuilder.success(ResponseConstants.MESSAGE_200_UPDATE, updatedSubject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return  ResponseBuilder.success(ResponseConstants.MESSAGE_200_DELETE, null);
    }

    @GetMapping("/{subjectId}/exams")
    public ResponseEntity<ResponseDTO> getExamsForSubject(@PathVariable Long subjectId) {
        List<ExamSubjectDTO> exams = subjectService.getExamsForSubject(subjectId);
        return  ResponseBuilder.success(ResponseConstants.MESSAGE_200, exams);
    }
}