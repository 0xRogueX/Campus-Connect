package com.campusconnect.profileservice.mapper;

import com.campusconnect.profileservice.model.dto.*;
import com.campusconnect.profileservice.model.entity.*;

public class ProfileMapper {

    public static StudentProfile toStudentEntity(StudentProfileDto dto) {
        StudentProfile profile = new StudentProfile();
        profile.setUserId(dto.getUserId());
        profile.setFullName(dto.getFullName());
        profile.setEmail(dto.getEmail());
        profile.setPhone(dto.getPhone());
        profile.setAddress(dto.getAddress());
        profile.setGender(dto.getGender());
        profile.setDateOfBirth(dto.getDateOfBirth());
        profile.setEnrollmentNumber(dto.getEnrollmentNumber());
        profile.setCourse(dto.getCourse());
        profile.setBatch(dto.getBatch());
        return profile;
    }

    public static FacultyProfile toFacultyEntity(FacultyProfileDto dto) {
        FacultyProfile profile = new FacultyProfile();
        profile.setUserId(dto.getUserId());
        profile.setFullName(dto.getFullName());
        profile.setEmail(dto.getEmail());
        profile.setPhone(dto.getPhone());
        profile.setAddress(dto.getAddress());
        profile.setGender(dto.getGender());
        profile.setDateOfBirth(dto.getDateOfBirth());
        profile.setEmployeeId(dto.getEmployeeId());
        profile.setDepartment(dto.getDepartment());
        profile.setDesignation(dto.getDesignation());
        return profile;
    }

    public static AdminProfile toAdminEntity(AdminProfileDto dto) {
        AdminProfile profile = new AdminProfile();
        profile.setUserId(dto.getUserId());
        profile.setFullName(dto.getFullName());
        profile.setEmail(dto.getEmail());
        profile.setPhone(dto.getPhone());
        profile.setAddress(dto.getAddress());
        profile.setGender(dto.getGender());
        profile.setDateOfBirth(dto.getDateOfBirth());
        profile.setAdminId(dto.getAdminId());
        profile.setRole(dto.getRole());
        return profile;
    }

    public static BaseProfileDto toDto(ProfileBase entity) {
        if (entity instanceof StudentProfile student) {
            StudentProfileDto dto = new StudentProfileDto();
            dto.setUserId(student.getUserId());
            dto.setFullName(student.getFullName());
            dto.setEmail(student.getEmail());
            dto.setPhone(student.getPhone());
            dto.setAddress(student.getAddress());
            dto.setGender(student.getGender());
            dto.setDateOfBirth(student.getDateOfBirth());
            dto.setEnrollmentNumber(student.getEnrollmentNumber());
            dto.setCourse(student.getCourse());
            dto.setBatch(student.getBatch());
            return dto;
        } else if (entity instanceof FacultyProfile faculty) {
            FacultyProfileDto dto = new FacultyProfileDto();
            dto.setUserId(faculty.getUserId());
            dto.setFullName(faculty.getFullName());
            dto.setEmail(faculty.getEmail());
            dto.setPhone(faculty.getPhone());
            dto.setAddress(faculty.getAddress());
            dto.setGender(faculty.getGender());
            dto.setDateOfBirth(faculty.getDateOfBirth());
            dto.setEmployeeId(faculty.getEmployeeId());
            dto.setDepartment(faculty.getDepartment());
            dto.setDesignation(faculty.getDesignation());
            return dto;
        } else if (entity instanceof AdminProfile admin) {
            AdminProfileDto dto = new AdminProfileDto();
            dto.setUserId(admin.getUserId());
            dto.setFullName(admin.getFullName());
            dto.setEmail(admin.getEmail());
            dto.setPhone(admin.getPhone());
            dto.setAddress(admin.getAddress());
            dto.setGender(admin.getGender());
            dto.setDateOfBirth(admin.getDateOfBirth());
            dto.setAdminId(admin.getAdminId());
            dto.setRole(admin.getRole());
            return dto;
        } else {
            throw new IllegalArgumentException("Unknown profile entity type");
        }
    }
}
