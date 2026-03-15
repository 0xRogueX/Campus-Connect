package com.campusconnect.profileservice.controller;

import com.campusconnect.profileservice.model.dto.BaseProfileDto;
import com.campusconnect.profileservice.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/healthcheck")
    public String healthCheck() {
        return "OK";
    }

    @GetMapping("/{userId}")
    public BaseProfileDto getProfile(@PathVariable String userId) {
        return profileService.getProfileByUserId(userId);
    }

    @PutMapping("/{userId}")
    public void updateProfile(@PathVariable String userId,
                              @RequestBody BaseProfileDto profileDto) {
        profileService.updateProfile(userId, profileDto);
    }


    @PostMapping
    public BaseProfileDto createProfile(@RequestBody BaseProfileDto profileDto) {
        return profileService.createProfile(profileDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteProfile(@PathVariable String userId) {
        profileService.deleteProfile(userId);
    }
}

