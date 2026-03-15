package com.campusconnect.courseservice.controller;

import com.campusconnect.courseservice.model.dto.BranchDto;
import com.campusconnect.courseservice.model.dto.SubjectDto;
import com.campusconnect.courseservice.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/branches")
@Validated
public class BranchController {

    @Autowired
    private BranchService branchService;


    @GetMapping("/healthcheck")
    public String healthCheck() {
        return "OK";
    }


    @GetMapping
    public List<BranchDto> getAll() {
        return branchService.getAllBranches();
    }

    @GetMapping("/{id}")
    public BranchDto getById(@PathVariable String id ) {
        return branchService.getBranchById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BranchDto create(@Valid @RequestBody BranchDto branchDto ,@RequestHeader("Authorization") String token) {
        return branchService.createBranch(branchDto ,token);
    }

    @PutMapping("/{id}")
    public BranchDto update(@PathVariable String id, @Valid @RequestBody BranchDto branchDto ,@RequestHeader("Authorization") String token) {
        return branchService.updateBranch(id, branchDto ,token);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id ,@RequestHeader("Authorization") String token) {
        branchService.deleteBranch(id ,token);
    }

    @PostMapping("/{branchId}/subjects")
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectDto addSubject(
            @PathVariable String branchId,
            @Valid @RequestBody SubjectDto subjectDto,@RequestHeader("Authorization") String token) {
        return branchService.addSubject(branchId, subjectDto ,token);
    }

    @PutMapping("/{branchId}/subjects/{subjectId}")
    public SubjectDto updateSubject(
            @PathVariable String branchId,
            @PathVariable String subjectId,
            @Valid @RequestBody SubjectDto subjectDto,@RequestHeader("Authorization") String token) {
        return branchService.updateSubject(branchId, subjectId, subjectDto ,token);
    }

    @DeleteMapping("/{branchId}/subjects/{subjectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubject(
            @PathVariable String branchId,
            @PathVariable String subjectId,@RequestHeader("Authorization") String token) {
        branchService.deleteSubject(branchId, subjectId ,token);
    }
}
