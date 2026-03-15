package com.campusconnect.examservice.service.impl;

import com.campusconnect.examservice.dto.ExamSubjectDTO;
import com.campusconnect.examservice.dto.SubjectDTO;
import com.campusconnect.examservice.entity.Exam;
import com.campusconnect.examservice.entity.ExamSubject;
import com.campusconnect.examservice.entity.Subject;
import com.campusconnect.examservice.exception.ExamAlreadyExistsException;
import com.campusconnect.examservice.exception.InvalidExamDataException;
import com.campusconnect.examservice.exception.ResourceNotFoundException;
import com.campusconnect.examservice.mapper.ExamSubjectMapper;
import com.campusconnect.examservice.repository.ExamRepository;
import com.campusconnect.examservice.repository.ExamSubjectRepository;
import com.campusconnect.examservice.repository.SubjectRepository;
import com.campusconnect.examservice.service.ISubjectService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements ISubjectService {

    private final SubjectRepository subjectRepository;
    private final ExamSubjectRepository examSubjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository, ExamSubjectRepository examSubjectRepository) {
        this.subjectRepository = subjectRepository;
        this.examSubjectRepository = examSubjectRepository;
    }

    @Override
    @Transactional
    public SubjectDTO createSubject(SubjectDTO subjectDTO) {
        validateSubjectData(subjectDTO);

        // Check if subject code already exists
        Subject existingSubject = subjectRepository.findBySubjectCode(subjectDTO.getSubjectCode());
        if (existingSubject != null) {
            throw new ExamAlreadyExistsException("Subject with code " + subjectDTO.getSubjectCode() + " already exists");
        }

        Subject subject = convertToEntity(subjectDTO);
        Subject savedSubject = subjectRepository.save(subject);
        return convertToDTO(savedSubject);
    }
    @Override
    @Transactional
    public List<SubjectDTO> createSubjects(List<SubjectDTO> subjectDTOs) {
        List<SubjectDTO> createdSubjects = new ArrayList<>();

        for (SubjectDTO subjectDTO : subjectDTOs) {
            validateSubjectData(subjectDTO);

            // Check if subject code already exists
            Subject existingSubject = subjectRepository.findBySubjectCode(subjectDTO.getSubjectCode());
            if (existingSubject != null) {
                throw new ExamAlreadyExistsException("Subject with code " + subjectDTO.getSubjectCode() + " already exists");
            }

            Subject subject = convertToEntity(subjectDTO);
            Subject savedSubject = subjectRepository.save(subject);
            createdSubjects.add(convertToDTO(savedSubject));
        }

        return createdSubjects;
    }
    @Override
    public SubjectDTO getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));
        return convertToDTO(subject);
    }

    @Override
    public SubjectDTO getSubjectByCode(String code) {
        Subject subject = subjectRepository.findBySubjectCode(code);
        if (subject == null) {
            throw new ResourceNotFoundException("Subject not found with code: " + code);
        }
        return convertToDTO(subject);
    }

    @Override
    public List<SubjectDTO> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SubjectDTO updateSubject(Long id, SubjectDTO subjectDTO) {
        Subject existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));

        validateSubjectData(subjectDTO);

        // Check if the updated subject code conflicts with another subject
        Subject subjectWithSameCode = subjectRepository.findBySubjectCode(subjectDTO.getSubjectCode());
        if (subjectWithSameCode != null && !subjectWithSameCode.getId().equals(id)) {
            throw new ExamAlreadyExistsException("Subject with code " + subjectDTO.getSubjectCode() + " already exists");
        }

        existingSubject.setSubjectName(subjectDTO.getSubjectName());
        existingSubject.setSubjectCode(subjectDTO.getSubjectCode());
        existingSubject.setCredits(subjectDTO.getCredits());

        Subject updatedSubject = subjectRepository.save(existingSubject);
        return convertToDTO(updatedSubject);
    }

    @Override
    @Transactional
    public void deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subject not found with id: " + id);
        }

        // Check if the subject is used in any exams
        List<ExamSubject> examSubjects = examSubjectRepository.findBySubjectId(id);
        if (!examSubjects.isEmpty()) {
            throw new InvalidExamDataException("Cannot delete subject as it is used in " + examSubjects.size() + " exams");
        }

        subjectRepository.deleteById(id);
    }

    @Override
    public List<ExamSubjectDTO> getExamsForSubject(Long subjectId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found with id: " + subjectId);
        }

        List<ExamSubject> examSubjects = examSubjectRepository.findBySubjectId(subjectId);
        return examSubjects.stream()
                .map(this::convertToExamSubjectDTO)
                .collect(Collectors.toList());
    }

    private void validateSubjectData(SubjectDTO subjectDTO) {
        if (subjectDTO.getSubjectName() == null || subjectDTO.getSubjectName().trim().isEmpty()) {
            throw new InvalidExamDataException("Subject name cannot be empty");
        }

        if (subjectDTO.getSubjectCode() == null || subjectDTO.getSubjectCode().trim().isEmpty()) {
            throw new InvalidExamDataException("Subject code cannot be empty");
        }

        if (subjectDTO.getCredits() == null || subjectDTO.getCredits() <= 0) {
            throw new InvalidExamDataException("Subject credits must be positive");
        }
    }

    private Subject convertToEntity(SubjectDTO subjectDTO) {
        Subject subject = new Subject();
        subject.setId(subjectDTO.getId());
        subject.setSubjectName(subjectDTO.getSubjectName());
        subject.setSubjectCode(subjectDTO.getSubjectCode());
        subject.setCredits(subjectDTO.getCredits());
        return subject;
    }

    private SubjectDTO convertToDTO(Subject subject) {
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(subject.getId());
        subjectDTO.setSubjectName(subject.getSubjectName());
        subjectDTO.setSubjectCode(subject.getSubjectCode());
        subjectDTO.setCredits(subject.getCredits());
        return subjectDTO;
    }

    private ExamSubjectDTO convertToExamSubjectDTO(ExamSubject examSubject) {
        ExamSubjectDTO dto = new ExamSubjectDTO();
        dto.setId(examSubject.getId());
        dto.setExamId(examSubject.getExam().getId());
        dto.setSubjectId(examSubject.getSubject().getId());
        dto.setSubjectName(examSubject.getSubject().getSubjectName());
        dto.setSubjectCode(examSubject.getSubject().getSubjectCode());
        dto.setCredits(examSubject.getSubject().getCredits());
        dto.setExamDate(examSubject.getExamDate());
        dto.setStartTime(examSubject.getStartTime());
        dto.setEndTime(examSubject.getEndTime());
        return dto;
    }
}
