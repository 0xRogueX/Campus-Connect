package com.campusconnect.resultservice.service.impl;

import com.campusconnect.resultservice.dto.SubjectDTO;
import com.campusconnect.resultservice.entity.Result;
import com.campusconnect.resultservice.entity.Subject;
import com.campusconnect.resultservice.mapper.SubjectMapper;
import com.campusconnect.resultservice.repository.ResultRepository;
import com.campusconnect.resultservice.repository.SubjectRepository;
import com.campusconnect.resultservice.service.ISubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements ISubjectService {

    private final SubjectRepository subjectRepository;
    private final ResultRepository resultRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public List<SubjectDTO> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjectMapper.toDTOList(subjects);
    }

    @Override
    public Optional<SubjectDTO> getSubjectById(Integer subjectId) {
        return subjectRepository.findById(subjectId)
                .map(subjectMapper::toDTO);
    }

    @Override
    public List<SubjectDTO> getSubjectsByResultId(Integer resultId) {
        List<Subject> subjects = subjectRepository.findByResultResultId(resultId);
        return subjectMapper.toDTOList(subjects);
    }

    @Override
    public SubjectDTO saveSubject(SubjectDTO subjectDTO) {

        Result result = null;
        if (subjectDTO.getResultId() != null) {
            result = resultRepository.findById(subjectDTO.getResultId())
                    .orElseThrow(() -> new RuntimeException("Result not found with ID: " + subjectDTO.getResultId()));
        }

        // Convert to entity
        Subject subject = subjectMapper.toEntity(subjectDTO, result);


        for (Subject subject1 : result.getSubjects()) {
            subject1.setResult(result);
            subjectRepository.save(subject1);
        }
        // Save and return
        Subject savedSubject = subjectRepository.save(subject);
        return subjectMapper.toDTO(savedSubject);
    }

    @Override
    public void deleteSubject(Integer subjectId) {
        subjectRepository.deleteById(subjectId);
    }



}
