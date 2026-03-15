package com.campusconnect.resultservice.controller;

import com.campusconnect.resultservice.entity.Result;

import com.campusconnect.resultservice.service.impl.ResultPdfService;
import com.campusconnect.resultservice.service.impl.ResultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/results")
public class ResultPdfController {

    @Autowired
    private ResultServiceImpl resultService;

    @Autowired
    private ResultPdfService resultPdfService;

    @GetMapping("/{enrollmentNo}/pdf")
    public ResponseEntity<InputStreamResource> downloadResultPdf(@PathVariable Long enrollmentNo) {
        Result result = resultService.getResultByEnrollmentNo(enrollmentNo);

        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayInputStream pdf = resultPdfService.generateResultPdf(result);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=result_" + enrollmentNo + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }
}