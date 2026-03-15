package com.campusconnect.resultservice.controller;

import com.campusconnect.resultservice.constant.ResponseConstants;
import com.campusconnect.resultservice.dto.ResponseDTO;
import com.campusconnect.resultservice.dto.SubjectDTO;

import com.campusconnect.resultservice.service.ISubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/results/subjects")

public class SubjectController {

    private final ISubjectService subjectService;

    public SubjectController(ISubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllSubjects() {
        List<SubjectDTO> subjects = subjectService.getAllSubjects();

        ResponseDTO response = ResponseDTO.builder()
                .statusCode(ResponseConstants.STATUS_200)
                .statusMsg(ResponseConstants.MESSAGE_200_SUBJECT)
                .data(subjects)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<ResponseDTO> getSubjectById(@PathVariable Integer subjectId) {
        Optional<SubjectDTO> subject = subjectService.getSubjectById(subjectId);

        if (subject.isPresent()) {
            ResponseDTO response = ResponseDTO.builder()
                    .statusCode(ResponseConstants.STATUS_200)
                    .statusMsg(ResponseConstants.MESSAGE_200_SUBJECT)
                    .data(subject.get())
                    .build();

            return ResponseEntity.ok(response);
        } else {
            ResponseDTO response = ResponseDTO.builder()
                    .statusCode(ResponseConstants.STATUS_404)
                    .statusMsg(ResponseConstants.MESSAGE_404_SUBJECT)
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/result/{resultId}")
    public ResponseEntity<ResponseDTO> getSubjectsByResultId(@PathVariable Integer resultId) {
        List<SubjectDTO> subjects = subjectService.getSubjectsByResultId(resultId);

        ResponseDTO response = ResponseDTO.builder()
                .statusCode(ResponseConstants.STATUS_200)
                .statusMsg(ResponseConstants.MESSAGE_200_SUBJECT)
                .data(subjects)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createSubject(@RequestBody SubjectDTO subjectDTO) {
        try {
            SubjectDTO savedSubject = subjectService.saveSubject(subjectDTO);

            ResponseDTO response = ResponseDTO.builder()
                    .statusCode(ResponseConstants.STATUS_201)
                    .statusMsg(ResponseConstants.MESSAGE_201_SUBJECT)
                    .data(savedSubject)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            ResponseDTO response = ResponseDTO.builder()
                    .statusCode(ResponseConstants.STATUS_400)
                    .statusMsg(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<ResponseDTO> updateSubject(@PathVariable Integer subjectId, @RequestBody SubjectDTO subjectDTO) {
        Optional<SubjectDTO> existingSubject = subjectService.getSubjectById(subjectId);

        if (existingSubject.isPresent()) {
            subjectDTO.setSubjectId(subjectId);
            SubjectDTO updatedSubject = subjectService.saveSubject(subjectDTO);

            ResponseDTO response = ResponseDTO.builder()
                    .statusCode(ResponseConstants.STATUS_200)
                    .statusMsg(ResponseConstants.MESSAGE_200_UPDATE)
                    .data(updatedSubject)
                    .build();

            return ResponseEntity.ok(response);
        } else {
            ResponseDTO response = ResponseDTO.builder()
                    .statusCode(ResponseConstants.STATUS_404)
                    .statusMsg(ResponseConstants.MESSAGE_404_SUBJECT)
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<ResponseDTO> deleteSubject(@PathVariable Integer subjectId) {
        Optional<SubjectDTO> existingSubject = subjectService.getSubjectById(subjectId);

        if (existingSubject.isPresent()) {
            subjectService.deleteSubject(subjectId);

            ResponseDTO response = ResponseDTO.builder()
                    .statusCode(ResponseConstants.STATUS_200)
                    .statusMsg(ResponseConstants.MESSAGE_200_DELETE)
                    .build();

            return ResponseEntity.ok(response);
        } else {
            ResponseDTO response = ResponseDTO.builder()
                    .statusCode(ResponseConstants.STATUS_404)
                    .statusMsg(ResponseConstants.MESSAGE_404_SUBJECT)
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
