package com.campusconnect.resultservice.service.impl;

import com.campusconnect.resultservice.constant.SubjectCredits;
import com.campusconnect.resultservice.constant.SubjectGrades;
import com.campusconnect.resultservice.dto.CreateResultRequestDTO;
import com.campusconnect.resultservice.dto.ResultDTO;
import com.campusconnect.resultservice.dto.SubjectDTO;
import com.campusconnect.resultservice.entity.Result;
import com.campusconnect.resultservice.entity.Subject;
import com.campusconnect.resultservice.exception.ResourceNotFoundException;
import com.campusconnect.resultservice.mapper.ResultMapper;
import com.campusconnect.resultservice.mapper.SubjectMapper;
import com.campusconnect.resultservice.repository.ResultRepository;
import com.campusconnect.resultservice.service.IResultService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements IResultService {

    private final ResultRepository resultRepository;
    private final ResultMapper resultMapper;
    private final SubjectMapper subjectMapper;



    @Override
    @Transactional
    public ResultDTO createResult(ResultDTO resultDTO) {
        Result result = resultMapper.toEntity(resultDTO);
        BeanUtils.copyProperties(resultDTO, result);
        double spi = 0;
        double spi1 = 0;
        double cpi = 0;
        int totalMarks = 0;
        int obtainedMarks = 0;
        ArrayList<Double> spis = new ArrayList<>();



        List<Subject> subjects = subjectMapper.toEntityList(resultDTO.getSubjects(),result);

        for (Subject subject : subjects) {
            totalMarks  = SubjectCredits.subjectCredits.get(subject.getSubjectName().toUpperCase()) * SubjectGrades.subjectGrades.get("AA");
            System.out.println(subject.getSubjectName().toUpperCase());
            System.out.println(subject.getSubjectGrade().toUpperCase());
             obtainedMarks = SubjectCredits.subjectCredits.get(subject.getSubjectName().toUpperCase()) * SubjectGrades.subjectGrades.get(subject.getSubjectGrade().toUpperCase());



        }
        spi = (double) obtainedMarks / totalMarks *10;
         spis.add(spi);

         for (int i = 0; i < spis.size(); i++) {
            spi1 += spis.get(i);
             cpi = spi1 / (i+1);

         }

        result.setSpis(spis);
        result.setSpi(spi);
        result.setCpi(cpi);
        result.setCgpa(0.0);
        result.setDeclaredOn(LocalDate.now());

        result.setSubjects(subjects);
        resultRepository.save(result);
        resultDTO = resultMapper.toDTO(result);
        resultDTO.setResultId(result.getResultId());
        return resultDTO;
    }

    @Override
    public ResultDTO findByEnrollmentNo(Long enrollmentNo) {
        Optional<Result> result = Optional.ofNullable(resultRepository.findResultByEnrollmentNo(enrollmentNo));
        return result.map(resultMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("result", "enrollmentNo", enrollmentNo));
    }

    @Override
    public Result getResultByEnrollmentNo(Long enrollmentNo) {
        return resultRepository.findByEnrollmentNo(enrollmentNo).orElse(null);
    }




}