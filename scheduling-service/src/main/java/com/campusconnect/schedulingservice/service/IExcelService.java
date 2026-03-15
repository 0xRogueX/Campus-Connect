package com.campusconnect.schedulingservice.service;

import com.campusconnect.schedulingservice.entity.Schedule;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface IExcelService {
     void saveExcelData(MultipartFile file);
    List<Schedule> readExcel(InputStream inputStream) throws IOException;
     byte[] generateExcel();

}
