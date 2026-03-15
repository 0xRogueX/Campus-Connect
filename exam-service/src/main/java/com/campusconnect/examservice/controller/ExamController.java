package com.campusconnect.examservice.controller;


import com.campusconnect.examservice.constant.ResponseBuilder;
import com.campusconnect.examservice.constant.ResponseConstants;
import com.campusconnect.examservice.dto.ExamDTO;
import com.campusconnect.examservice.dto.ExamSubjectDTO;
import com.campusconnect.examservice.dto.ResponseDTO;
import com.campusconnect.examservice.service.IExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final IExamService examService;

    @Autowired
    public ExamController(IExamService examService) {
        this.examService = examService;
    }


    @GetMapping("/healthcheck")
    public String healthCheck() {
        return "OK";
    }


    @PostMapping
    public ResponseEntity<ResponseDTO> createExam(@RequestBody ExamDTO examDTO) {
        ExamDTO createdExam = examService.createExam(examDTO);
        return  ResponseBuilder.success(ResponseConstants.MESSAGE_201, createdExam);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getExamById(@PathVariable Long id) {
        ExamDTO exam = examService.getExamById(id);


        return ResponseBuilder.success(ResponseConstants.MESSAGE_200, exam);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllExams() {
        List<ExamDTO> exams = examService.getAllExams();
        return  ResponseBuilder.success(ResponseConstants.MESSAGE_200, exams);

    }

    @GetMapping("/semester/{semester}")
    public ResponseEntity<ResponseDTO> getExamsBySemester(@PathVariable String semester) {
        List<ExamDTO> exams = examService.getExamsBySemester(semester);
        return  ResponseBuilder.success(ResponseConstants.MESSAGE_200, exams);
    }

    @GetMapping("/branch/{branch}")
    public ResponseEntity<ResponseDTO> getExamsByBranch(@PathVariable String branch) {
        List<ExamDTO> exams = examService.getExamsByBranch(branch);
        return  ResponseBuilder.success(ResponseConstants.MESSAGE_200, exams);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateExam(@PathVariable Long id, @RequestBody ExamDTO examDTO) {
        ExamDTO updatedExam = examService.updateExam(id, examDTO);
        return  ResponseBuilder.success(ResponseConstants.MESSAGE_200_UPDATE, updatedExam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return  ResponseBuilder.success(ResponseConstants.MESSAGE_200_DELETE, null);
    }

    @PostMapping("/{examId}/subjects")
    public ResponseEntity<ResponseDTO> addSubjectToExam(@PathVariable Long examId, @RequestBody ExamSubjectDTO examSubjectDTO) {
        ExamSubjectDTO addedSubject = examService.addSubjectToExam(examId, examSubjectDTO);
        return  ResponseBuilder.success(ResponseConstants.MESSAGE_200, addedSubject);
    }

    @DeleteMapping("/{examId}/subjects/{subjectId}")
    public ResponseEntity<ResponseDTO> removeSubjectFromExam(@PathVariable Long examId, @PathVariable Long subjectId) {
        examService.removeSubjectFromExam(examId, subjectId);
        return  ResponseBuilder.success(ResponseConstants.MESSAGE_200_DELETE, null);
    }

    @GetMapping("/{examId}/subjects")
    public ResponseEntity<ResponseDTO> getAllSubjectsForExam(@PathVariable Long examId) {
        List<ExamSubjectDTO> subjects = examService.getAllSubjectsForExam(examId);
        return ResponseBuilder.success(ResponseConstants.MESSAGE_200, subjects);
    }
}