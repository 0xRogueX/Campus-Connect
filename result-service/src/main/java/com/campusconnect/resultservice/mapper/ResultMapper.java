package com.campusconnect.resultservice.mapper;

import com.campusconnect.resultservice.dto.ResultDTO;
import com.campusconnect.resultservice.entity.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ResultMapper {

    private final SubjectMapper subjectMapper;

    public ResultDTO toDTO(Result result) {
        if (result == null) {
            return null;
        }

        ResultDTO dto = new ResultDTO();
        dto.setResultId(result.getResultId());
        dto.setDeclaredOn(result.getDeclaredOn());
        dto.setExam(result.getExam());
        dto.setCgpa(result.getCgpa());
        dto.setCpi(result.getCpi());
        dto.setSpi(result.getSpi());
        dto.setIsPass(result.getIsPass());
        dto.setStudentId(result.getStudentId());
        dto.setEnrollmentNo(result.getEnrollmentNo());
        dto.setBranch(result.getBranch());
        dto.setSemester(result.getSemester());


        if (result.getSubjects() != null) {
            dto.setSubjects(subjectMapper.toDTOList(result.getSubjects()));
        }

        return dto;
    }

    public Result toEntity(ResultDTO dto) {
        if (dto == null) {
            return null;
        }

        Result result = new Result();
        result.setResultId(dto.getResultId());
        result.setDeclaredOn(dto.getDeclaredOn());
        result.setExam(dto.getExam());
        result.setCgpa(dto.getCgpa());
        result.setCpi(dto.getCpi());
        result.setSpi(dto.getSpi());
        result.setIsPass(dto.getIsPass());
        result.setStudentId(dto.getStudentId());
        result.setEnrollmentNo(dto.getEnrollmentNo());
        result.setBranch(dto.getBranch());
        result.setSemester(dto.getSemester());

        // We'll handle subjects separately to manage the bidirectional relationship

        return result;
    }

    // Method to handle bidirectional relationship with subjects
   public Result toCompleteEntity(ResultDTO dto) {
        Result result = toEntity(dto);

        if (result != null && dto.getSubjects() != null) {
            result.setSubjects(subjectMapper.toEntityList(dto.getSubjects(), result));
        }

        return result;
    }

    public List<ResultDTO> toDTOList(List<Result> results) {
        return results.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    public List<Result> toEntityList(List<ResultDTO> resultDTO) {
        return resultDTO.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}