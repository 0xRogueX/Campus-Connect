package com.campusconnect.examservice.service.impl;

import com.campusconnect.examservice.dto.ExamDTO;
import com.campusconnect.examservice.dto.ExamSubjectDTO;
import com.campusconnect.examservice.entity.Exam;
import com.campusconnect.examservice.entity.ExamSubject;
import com.campusconnect.examservice.entity.Subject;
import com.campusconnect.examservice.exception.ExamScheduleConflictException;
import com.campusconnect.examservice.exception.InvalidExamDataException;
import com.campusconnect.examservice.exception.ResourceNotFoundException;
import com.campusconnect.examservice.mapper.ExamMapper;
import com.campusconnect.examservice.mapper.ExamSubjectMapper;
import com.campusconnect.examservice.repository.ExamRepository;
import com.campusconnect.examservice.repository.ExamSubjectRepository;
import com.campusconnect.examservice.repository.SubjectRepository;
import com.campusconnect.examservice.service.IExamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class ExamServiceImpl implements IExamService {

    private final ExamRepository examRepository;
    private final SubjectRepository subjectRepository;
    private final ExamSubjectRepository examSubjectRepository;

    public ExamServiceImpl(ExamRepository examRepository, SubjectRepository subjectRepository,
                           ExamSubjectRepository examSubjectRepository) {
        this.examRepository = examRepository;
        this.subjectRepository = subjectRepository;
        this.examSubjectRepository = examSubjectRepository;
    }

    @Override
    @Transactional
    public ExamDTO createExam(ExamDTO examDTO) {
        validateExamData(examDTO);

        // Create and save the exam entity
        Exam exam = new Exam();
        exam.setExamName(examDTO.getExamName());
        exam.setExamStart(examDTO.getExamStart());
        exam.setExamEnd(examDTO.getExamEnd());
        exam.setExamDuration(examDTO.getExamDuration());
        exam.setSemester(examDTO.getSemester());
        exam.setBranch(examDTO.getBranch());

        Exam savedExam = examRepository.save(exam);

        // Handle subjects if provided
        Set<ExamSubjectDTO> subjectDTOs = examDTO.getSubjects();
        if (subjectDTOs != null && !subjectDTOs.isEmpty()) {
            Set<ExamSubjectDTO> savedSubjects = new HashSet<>();

            // Check for scheduling conflicts among subjects
            validateSubjectSchedules(subjectDTOs);

            for (ExamSubjectDTO subjectDTO : subjectDTOs) {
                // Verify subject exists
                Subject subject = subjectRepository.findById(subjectDTO.getSubjectId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Subject not found with id: " + subjectDTO.getSubjectId()));

                // Create exam subject link
                ExamSubject examSubject = new ExamSubject();
                examSubject.setExam(savedExam);
                examSubject.setSubject(subject);
                examSubject.setExamDate(subjectDTO.getExamDate());
                examSubject.setStartTime(subjectDTO.getStartTime());
                examSubject.setEndTime(subjectDTO.getEndTime());

                ExamSubject savedExamSubject = examSubjectRepository.save(examSubject);
                savedSubjects.add(ExamSubjectMapper.toDTO(savedExamSubject));
            }

            // Now create the complete result DTO
            ExamDTO resultDTO = ExamMapper.toDTO(savedExam);
            resultDTO.setSubjects(savedSubjects);
            return resultDTO;
        }

        // If no subjects, just return the exam
        return ExamMapper.toDTO(savedExam);
    }

    private void validateSubjectSchedules(Set<ExamSubjectDTO> subjects) {
        List<ExamSubjectDTO> subjectsList = new ArrayList<>(subjects);

        for (int i = 0; i < subjectsList.size(); i++) {
            ExamSubjectDTO subject1 = subjectsList.get(i);

            for (int j = i + 1; j < subjectsList.size(); j++) {
                ExamSubjectDTO subject2 = subjectsList.get(j);

                // Check if dates match and times overlap
                if (subject1.getExamDate().equals(subject2.getExamDate())) {
                    if (timesOverlap(
                            subject1.getStartTime(), subject1.getEndTime(),
                            subject2.getStartTime(), subject2.getEndTime())) {
                        throw new ExamScheduleConflictException(
                                "Schedule conflict detected between subjects in the request");
                    }
                }
            }
        }
    }

    private boolean timesOverlap(
            java.time.LocalDateTime start1, java.time.LocalDateTime end1,
            java.time.LocalDateTime start2, java.time.LocalDateTime end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }
    @Override
    public ExamDTO getExamById(Long id) {
        Exam exam = examRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + id));
        return ExamMapper.toDTO(exam);
    }


    @Override
    public List<ExamDTO> getAllExams() {
        List<Exam> exams = examRepository.findAll();
        return ExamMapper.toDTOList(exams);
    }

    @Override
    public List<ExamDTO> getExamsBySemester(String semester) {
        List<Exam> exams = examRepository.findBySemester(semester);
        return ExamMapper.toDTOList(exams);
    }

    @Override
    public List<ExamDTO> getExamsByBranch(String branch) {
        List<Exam> exams = examRepository.findByBranch(branch);
        return ExamMapper.toDTOList(exams);
    }

    @Override
    @Transactional
    public ExamDTO updateExam(Long id, ExamDTO examDTO) {
        Exam existingExam = examRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + id));

        validateExamData(examDTO);

        existingExam.setExamName(examDTO.getExamName());
        existingExam.setExamStart(examDTO.getExamStart());
        existingExam.setExamEnd(examDTO.getExamEnd());
        existingExam.setExamDuration(examDTO.getExamDuration());
        existingExam.setSemester(examDTO.getSemester());
        existingExam.setBranch(examDTO.getBranch());

        Exam updatedExam = examRepository.save(existingExam);
        return ExamMapper.toDTO(updatedExam);
    }

    @Override
    @Transactional
    public void deleteExam(Long id) {
        if (!examRepository.existsById(id)) {
            throw new ResourceNotFoundException("Exam not found with id: " + id);
        }
        examRepository.deleteById(id);
    }



    @Override
    @Transactional
    public ExamSubjectDTO addSubjectToExam(Long examId, ExamSubjectDTO examSubjectDTO) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + examId));

        Subject subject = subjectRepository.findById(examSubjectDTO.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + examSubjectDTO.getSubjectId()));

        // Check for scheduling conflicts
        if (hasScheduleConflict(exam, examSubjectDTO)) {
            throw new ExamScheduleConflictException("The proposed exam schedule conflicts with existing subjects");
        }

        ExamSubject examSubject = new ExamSubject();
        examSubject.setExam(exam);
        examSubject.setSubject(subject);
        examSubject.setExamDate(examSubjectDTO.getExamDate());
        examSubject.setStartTime(examSubjectDTO.getStartTime());
        examSubject.setEndTime(examSubjectDTO.getEndTime());

        ExamSubject savedExamSubject = examSubjectRepository.save(examSubject);
        return ExamSubjectMapper.toDTO(savedExamSubject);
    }

    @Override
    @Transactional
    public void removeSubjectFromExam(Long examId, Long subjectId) {
        List<ExamSubject> examSubjects = examSubjectRepository.findByExamId(examId);
        ExamSubject examSubjectToRemove = examSubjects.stream()
                .filter(es -> es.getSubject().getId().equals(subjectId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found in the exam"));

        examSubjectRepository.delete(examSubjectToRemove);
    }

    @Override
    public List<ExamSubjectDTO> getAllSubjectsForExam(Long examId) {
        if (!examRepository.existsById(examId)) {
            throw new ResourceNotFoundException("Exam not found with id: " + examId);
        }

        List<ExamSubject> examSubjects = examSubjectRepository.findByExamId(examId);
        return ExamSubjectMapper.toDTOList(examSubjects);
    }

    private boolean hasScheduleConflict(Exam exam, ExamSubjectDTO newSubject) {
        List<ExamSubject> existingSubjects = examSubjectRepository.findByExamId(exam.getId());

        return existingSubjects.stream()
                .anyMatch(existingSubject ->
                        existingSubject.getExamDate().equals(newSubject.getExamDate()) &&
                                (
                                        (existingSubject.getStartTime().isBefore(newSubject.getEndTime()) &&
                                                existingSubject.getEndTime().isAfter(newSubject.getStartTime()))
                                )
                );
    }

    private void validateExamData(ExamDTO examDTO) {
        if (examDTO.getExamName() == null || examDTO.getExamName().trim().isEmpty()) {
            throw new InvalidExamDataException("Exam name cannot be empty");
        }

        if (examDTO.getExamStart() == null) {
            throw new InvalidExamDataException("Exam start date cannot be null");
        }

        if (examDTO.getExamEnd() == null) {
            throw new InvalidExamDataException("Exam end date cannot be null");
        }

        if (examDTO.getExamStart().isAfter(examDTO.getExamEnd())) {
            throw new InvalidExamDataException("Exam start date cannot be after exam end date");
        }

        if (examDTO.getExamDuration() == null || examDTO.getExamDuration() <= 0) {
            throw new InvalidExamDataException("Exam duration must be positive");
        }
    }

}