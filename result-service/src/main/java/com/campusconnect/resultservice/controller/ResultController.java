package com.campusconnect.resultservice.controller;

import com.campusconnect.resultservice.constant.ResponseConstants;
import com.campusconnect.resultservice.dto.CreateResultRequestDTO;
import com.campusconnect.resultservice.dto.ResponseDTO;
import com.campusconnect.resultservice.dto.ResultDTO;

import com.campusconnect.resultservice.service.IResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    private final IResultService resultService;

    public ResultController(IResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("/healthcheck")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createResult(@RequestBody ResultDTO resultDTO) {
        try {
            ResultDTO savedResult = resultService.createResult(resultDTO);

            ResponseDTO response = ResponseDTO.builder()
                    .statusCode(ResponseConstants.STATUS_201)
                    .statusMsg(ResponseConstants.MESSAGE_201_RESULT)
                    .data(savedResult)
                    .status(true)
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            ResponseDTO response = ResponseDTO.builder()
                    .statusCode(ResponseConstants.STATUS_400)
                    .statusMsg(e.getMessage())
                    .status(false)
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/enrollment/{enrollmentId}")
    public ResponseEntity<ResponseDTO> findByEnrollmentId(@PathVariable Long enrollmentId) {
        ResultDTO results = resultService.findByEnrollmentNo(enrollmentId);
        ResponseDTO response = ResponseDTO.builder()
                .statusCode(ResponseConstants.STATUS_200)
                .statusMsg(ResponseConstants.MESSAGE_200_RESULT)
                .data(results)
                .build();
        return ResponseEntity.ok(response);
    }

}
