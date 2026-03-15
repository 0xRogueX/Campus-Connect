package com.campusconnect.schedulingservice.controller;

import com.campusconnect.schedulingservice.service.IExcelService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/schedule/excel")
public class ExcelController {




        private final IExcelService excelService;

    public ExcelController(IExcelService excelService) {
        this.excelService = excelService;
    }


    @GetMapping("/download")
        public ResponseEntity<byte[]> downloadExcel() {
            byte[] excelBytes = excelService.generateExcel();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(excelBytes);
        }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        excelService.saveExcelData(file);
        return ResponseEntity.ok("Excel data uploaded successfully");
    }

}