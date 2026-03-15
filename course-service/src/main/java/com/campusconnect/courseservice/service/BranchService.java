package com.campusconnect.courseservice.service;

import com.campusconnect.courseservice.model.dto.BranchDto;
import com.campusconnect.courseservice.model.dto.SubjectDto;

import java.util.List;

public interface BranchService {
    List<BranchDto> getAllBranches();
    BranchDto getBranchById(String id );
    BranchDto createBranch(BranchDto branchDto , String token);
    BranchDto updateBranch(String id, BranchDto branchDto , String token);
    void deleteBranch(String id , String token);

    SubjectDto addSubject(String branchId, SubjectDto subjectDto , String token);
    SubjectDto updateSubject(String branchId, String subjectId, SubjectDto subjectDto , String token);
    void deleteSubject(String branchId, String subjectId , String token);
}
