package com.campusconnect.profileservice.service.Impl;

import com.campusconnect.profileservice.mapper.ProfileMapper;
import com.campusconnect.profileservice.model.dto.*;
import com.campusconnect.profileservice.model.entity.ProfileBase;
import com.campusconnect.profileservice.repository.ProfileRepository;
import com.campusconnect.profileservice.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public BaseProfileDto getProfileByUserId(String userId) {
        ProfileBase profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return ProfileMapper.toDto(profile);
    }

    @Override
    public void updateProfile(String userId, BaseProfileDto profileDto) {
        ProfileBase profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        // TODO: update fields from profileDto into profile (use a mapper or manual)
        profileRepository.save(profile);
    }

    @Override
    public BaseProfileDto createProfile(BaseProfileDto profileDto) {
        ProfileBase entity;
        if (profileDto instanceof StudentProfileDto studentDto) {
            entity = ProfileMapper.toStudentEntity(studentDto);
        } else if (profileDto instanceof FacultyProfileDto facultyDto) {
            entity = ProfileMapper.toFacultyEntity(facultyDto);
        } else if (profileDto instanceof AdminProfileDto adminDto) {
            entity = ProfileMapper.toAdminEntity(adminDto);
        } else {
            throw new IllegalArgumentException("Unsupported profile type");
        }
        ProfileBase saved = profileRepository.save(entity);
        return ProfileMapper.toDto(saved);
    }

    @Override
    public void deleteProfile(String userId) {
        ProfileBase profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        profileRepository.delete(profile);
    }
}
