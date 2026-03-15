package com.campusconnect.schedulingservice.service.impl;


import com.campusconnect.schedulingservice.entity.Schedule;
import com.campusconnect.schedulingservice.repository.ScheduleRepository;
import com.campusconnect.schedulingservice.service.IExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



@Service
public class ExcelServiceImpl implements IExcelService {
    private final ScheduleRepository scheduleRepository;

    public ExcelServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public void saveExcelData(MultipartFile file) {
        try {
            List<Schedule> students = readExcel(file.getInputStream());
            scheduleRepository.saveAll(students);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + e.getMessage());
        }
    }

    @Override
    public List<Schedule> readExcel(InputStream inputStream) throws IOException {
        List<Schedule> schedules = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header row

            Schedule schedule = new Schedule();

            for (Cell cell : row) {
                switch (cell.getColumnIndex()) {
                    case 0:
                        // Let the database handle the ID assignment (if auto-incremented)
                        break;
                    case 1:
                        schedule.setBranch(cell.getStringCellValue());
                        break;
                    case 2:
                        schedule.setDayOfWeek(cell.getStringCellValue());
                        break;
                    case 3:
                        schedule.setDivision(cell.getStringCellValue());
                        break;
                    case 4:
                        schedule.setEndTime(cell.getStringCellValue());
                        break;
                    case 5:
                        schedule.setFaculty(cell.getStringCellValue());
                        break;
                    case 6:
                        schedule.setLectureId(cell.getStringCellValue());
                        break;
                    case 7:
                        schedule.setRoom(cell.getStringCellValue());
                        break;
                    case 8:
                        schedule.setSemester((int) cell.getNumericCellValue());
                        break;
                    case 9:
                        schedule.setStartTime(cell.getStringCellValue());
                        break;
                    case 10:
                        schedule.setSubject(cell.getStringCellValue());
                        break;
                    default:
                        break;
                }
            }

            schedules.add(schedule);
        }

        scheduleRepository.saveAll(schedules);

        workbook.close();
        return schedules;
    }


    @Override
    public byte[] generateExcel() {
        List<Schedule> schedules = scheduleRepository.findAll();
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Schedule");
            Row headerRow = sheet.createRow(0);
            String[] columns = {"id", "branch", "day_of_week", "division","end_time", "faculty","lecture_id", "room", "semester", "start_time", "subject"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                CellStyle headerStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                headerStyle.setFont(font);
                cell.setCellStyle(headerStyle);
            }
            // Data Rows
            int rowNum = 1;
            for (Schedule schedule :schedules ) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(schedule.getId());
                row.createCell(1).setCellValue(schedule.getBranch());
                row.createCell(2).setCellValue(schedule.getDayOfWeek());
                row.createCell(3).setCellValue(schedule.getDivision());
                row.createCell(4).setCellValue(schedule.getEndTime());
                row.createCell(5).setCellValue(schedule.getFaculty());
                row.createCell(6).setCellValue(schedule.getSemester()+schedule.getDivision()+String.valueOf(rowNum-1));
                row.createCell(7).setCellValue(schedule.getRoom());
                row.createCell(8).setCellValue(schedule.getSemester());
                row.createCell(9).setCellValue(schedule.getStartTime());
                row.createCell(10).setCellValue(schedule.getSubject());
            }
            workbook.write(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Error while generating Excel", e);
        }    }
}
