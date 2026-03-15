package com.campusconnect.courseservice.service.Impl;

import com.campusconnect.courseservice.exception.UnauthorizedAccessException;
import com.campusconnect.courseservice.exception.ResourceNotFoundException;
import com.campusconnect.courseservice.model.dto.BranchDto;
import com.campusconnect.courseservice.model.dto.SubjectDto;
import com.campusconnect.courseservice.model.dto.SyllabusDto;
import com.campusconnect.courseservice.model.entity.Branch;
import com.campusconnect.courseservice.model.entity.Subject;
import com.campusconnect.courseservice.model.entity.Syllabus;
import com.campusconnect.courseservice.repository.BranchRepository;
import com.campusconnect.courseservice.repository.SubjectRepository;
import com.campusconnect.courseservice.repository.SyllabusRepository;
import com.campusconnect.courseservice.service.BranchService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepo;

    @Autowired
    private SubjectRepository subjectRepo;

    @Autowired
    private SyllabusRepository syllabusRepo;

    private final JWTService jwtService;

    public BranchServiceImpl(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public List<BranchDto> getAllBranches() {
        return branchRepo.findAll().stream()
                .map(this::assembleBranchDto)
                .collect(Collectors.toList());
    }

    @Override
    public BranchDto getBranchById(String id) {
        Branch b = branchRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found: " + id));
        return assembleBranchDto(b);
    }

    @Override
    @Transactional
    public BranchDto createBranch(BranchDto dto, String token) {
        if (!jwtService.isAdmin(token)) {
            throw new UnauthorizedAccessException("Only admin can perform this action.");
        }

        // 1. Save the Branch (excluding ID and nested subjects)
        Branch branch = new Branch();
        BeanUtils.copyProperties(dto, branch, "id", "subjects");
        Branch savedBranch = branchRepo.save(branch);

        // 2. Validate and persist any provided subjects
        List<SubjectDto> subjects = dto.getSubjects() != null
                ? dto.getSubjects().stream().filter(Objects::nonNull).collect(Collectors.toList())
                : Collections.emptyList();

        // Check for duplicate subject names
        Set<String> names = new HashSet<>();
        for (SubjectDto sub : subjects) {
            if (!names.add(sub.getName())) {
                throw new IllegalArgumentException("Duplicate subject name: " + sub.getName());
            }
        }

        if (!subjects.isEmpty()) {
            // Convert each SubjectDto to Subject entity
            List<Subject> toSaveSubjects = subjects.stream()
                    .map(subjectDto -> {
                        Subject subj = new Subject();
                        BeanUtils.copyProperties(subjectDto, subj, "id", "syllabus");
                        subj.setBranchId(savedBranch.getId());
                        return subj;
                    })
                    .collect(Collectors.toList());

            // Bulk save subjects
            List<Subject> savedSubjects = subjectRepo.saveAll(toSaveSubjects);

            // Build and save all Syllabus entities
            List<Syllabus> toSaveSyllabi = new ArrayList<>();
            for (int i = 0; i < savedSubjects.size(); i++) {
                Subject savedSubj = savedSubjects.get(i);
                SubjectDto subDto = subjects.get(i);
                for (SyllabusDto sylDto : subDto.getSyllabus()) {
                    if (sylDto == null) continue;
                    Syllabus syl = new Syllabus();
                    syl.setSubjectId(savedSubj.getId());
                    syl.setWeek(sylDto.getWeek());
                    syl.setTopics(sylDto.getTopics());
                    toSaveSyllabi.add(syl);
                }
            }
            syllabusRepo.saveAll(toSaveSyllabi);
        }

        // 3. Return the assembled DTO with nested subjects and syllabi
        return assembleBranchDto(savedBranch);
    }

    @Override
    public BranchDto updateBranch(String id, BranchDto dto, String token) {
        if (!jwtService.isAdminOrFaculty(token)) {
            throw new UnauthorizedAccessException("Only admin can perform this action.");
        }
        Branch existing = branchRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found: " + id));
        existing.setName(dto.getName());
        Branch updated = branchRepo.save(existing);
        return assembleBranchDto(updated);
    }

    @Override
    public void deleteBranch(String id, String token) {
        if (!jwtService.isAdminOrFaculty(token)) {
            throw new UnauthorizedAccessException("Only admin can perform this action.");
        }
        if (!branchRepo.existsById(id)) {
            throw new ResourceNotFoundException("Branch not found: " + id);
        }
        // Delete all subjects and their syllabi under this branch
        List<Subject> subs = subjectRepo.findByBranchId(id);
        subs.forEach(s -> syllabusRepo.deleteAll(syllabusRepo.findBySubjectId(s.getId())));
        subjectRepo.deleteAll(subs);
        branchRepo.deleteById(id);
    }

    @Override
    public SubjectDto addSubject(String branchId, SubjectDto subjectDto, String token) {
        if (!jwtService.isAdminOrFaculty(token)) {
            throw new UnauthorizedAccessException("Only admin can perform this action.");
        }
        Branch branch = branchRepo.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found: " + branchId));

        Subject subject = new Subject();
        BeanUtils.copyProperties(subjectDto, subject, "id");
        subject.setBranchId(branchId);
        Subject savedSubject = subjectRepo.save(subject);

        List<Syllabus> syllabusList = subjectDto.getSyllabus().stream()
                .filter(Objects::nonNull)
                .map(dto -> {
                    Syllabus s = new Syllabus();
                    s.setSubjectId(savedSubject.getId());
                    s.setWeek(dto.getWeek());
                    s.setTopics(dto.getTopics());
                    return s;
                }).collect(Collectors.toList());
        syllabusRepo.saveAll(syllabusList);

        SubjectDto result = new SubjectDto();
        BeanUtils.copyProperties(savedSubject, result);
        result.setSyllabus(subjectDto.getSyllabus());
        return result;
    }

    @Override
    public SubjectDto updateSubject(String branchId, String subjectId, SubjectDto subjectDto, String token) {
        if (!jwtService.isAdminOrFaculty(token)) {
            throw new UnauthorizedAccessException("Only admin can perform this action.");
        }
        Subject existingSubject = subjectRepo.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found: " + subjectId));

        if (!existingSubject.getBranchId().equals(branchId)) {
            throw new IllegalArgumentException("Subject does not belong to branch: " + branchId);
        }

        existingSubject.setName(subjectDto.getName());
        existingSubject.setSemester(subjectDto.getSemester());
        subjectRepo.save(existingSubject);

        // Replace existing syllabus entries
        syllabusRepo.deleteAll(syllabusRepo.findBySubjectId(subjectId));
        List<Syllabus> newSyllabus = subjectDto.getSyllabus().stream()
                .filter(Objects::nonNull)
                .map(dto -> new Syllabus(null, subjectId, dto.getWeek(), dto.getTopics()))
                .collect(Collectors.toList());
        syllabusRepo.saveAll(newSyllabus);

        SubjectDto result = new SubjectDto();
        BeanUtils.copyProperties(existingSubject, result);
        result.setSyllabus(subjectDto.getSyllabus());
        return result;
    }

    @Override
    public void deleteSubject(String branchId, String subjectId, String token) {
        if (!jwtService.isAdminOrFaculty(token)) {
            throw new UnauthorizedAccessException("Only admin can perform this action.");
        }
        Subject subject = subjectRepo.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found: " + subjectId));

        if (!subject.getBranchId().equals(branchId)) {
            throw new IllegalArgumentException("Subject does not belong to branch: " + branchId);
        }

        syllabusRepo.deleteAll(syllabusRepo.findBySubjectId(subjectId));
        subjectRepo.deleteById(subjectId);
    }

    // Helper to assemble nested DTOs
    private BranchDto assembleBranchDto(Branch b) {
        BranchDto dto = new BranchDto();
        BeanUtils.copyProperties(b, dto);

        List<Subject> subs = subjectRepo.findByBranchId(b.getId());
        List<SubjectDto> subDtos = subs.stream()
                .map(s -> {
                    SubjectDto sd = new SubjectDto();
                    BeanUtils.copyProperties(s, sd);
                    List<Syllabus> syll = syllabusRepo.findBySubjectId(s.getId());
                    List<SyllabusDto> sylDto = syll.stream()
                            .map(x -> new SyllabusDto(x.getWeek(), x.getTopics()))
                            .collect(Collectors.toList());
                    sd.setSyllabus(sylDto);
                    return sd;
                }).collect(Collectors.toList());

        dto.setSubjects(subDtos);
        return dto;
    }
}
