package com.campusconnect.profileservice.service;

import com.campusconnect.profileservice.model.dto.BaseProfileDto;

public interface ProfileService {
    BaseProfileDto getProfileByUserId(String userId);
    void updateProfile(String userId, BaseProfileDto profileDto);
    BaseProfileDto createProfile(BaseProfileDto profileDto);
    void deleteProfile(String userId);
}