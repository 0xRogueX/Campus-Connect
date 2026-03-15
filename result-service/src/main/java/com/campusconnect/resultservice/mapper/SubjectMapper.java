package com.campusconnect.resultservice.mapper;

import com.campusconnect.resultservice.dto.SubjectDTO;
import com.campusconnect.resultservice.entity.Result;
import com.campusconnect.resultservice.entity.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SubjectMapper {


    public SubjectDTO toDTO(Subject subject) {
        if (subject == null) {
            return null;
        }

        SubjectDTO dto = new SubjectDTO();
        dto.setSubjectId(subject.getSubjectId());
        dto.setSubjectCode(subject.getSubjectCode());
        dto.setSubjectName(subject.getSubjectName());
        dto.setIsAbsent(subject.getIsAbsent());
        dto.setSubjectGrade(subject.getSubjectGrade());
        dto.setPesa(subject.getPesa());
        dto.setPpaca(subject.getPpaca());
        dto.setPtotal(subject.getPtotal());
        dto.setTese(subject.getTese());
        dto.setTpaca(subject.getTpaca());
        dto.setTtotal(subject.getTtotal());
        dto.setCredit(subject.getCredit());


        if (subject.getResult() != null) {
            dto.setResultId(subject.getResult().getResultId());
        }

        return dto;
    }

    public Subject toEntity(SubjectDTO dto, Result result) {
        if (dto == null) {
            return null;
        }

        Subject subject = new Subject();
        subject.setSubjectCode(dto.getSubjectCode());
        subject.setSubjectName(dto.getSubjectName());
        subject.setIsAbsent(dto.getIsAbsent());
        subject.setSubjectGrade(dto.getSubjectGrade());
        subject.setResult(result);
        subject.setPesa(dto.getPesa());
        subject.setPpaca(dto.getPpaca());
        subject.setPtotal(dto.getPtotal());
        subject.setTese(dto.getTese());
        subject.setTpaca(dto.getTpaca());
        subject.setTtotal(dto.getTtotal());
        subject.setCredit(dto.getCredit());

        return subject;
    }

    public Subject toEntity(SubjectDTO dto) {
        return toEntity(dto, null);
    }

    public List<SubjectDTO> toDTOList(List<Subject> subjects) {
        return subjects.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Subject> toEntityList(List<SubjectDTO> dtos, Result result) {
        return dtos.stream()
                .map(dto -> toEntity(dto, result))
                .collect(Collectors.toList());
    }
}
