package com.campusconnect.resultservice.service;

import com.campusconnect.resultservice.dto.CreateResultRequestDTO;
import com.campusconnect.resultservice.dto.ResultDTO;
import com.campusconnect.resultservice.entity.Result;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IResultService  {
    ResultDTO createResult(ResultDTO resultDTO);
    ResultDTO findByEnrollmentNo(Long enrollmentNo);
    Result getResultByEnrollmentNo(Long enrollmentNo);


    //  ResultDTO updateResult(Long enrollmentNo, ResultDTO resultDTO);
}
