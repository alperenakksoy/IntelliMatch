package com.example.intellimatch.mapper;

import com.example.intellimatch.dto.job.JobPostingRequest;
import com.example.intellimatch.dto.job.JobPostingResponse;
import com.example.intellimatch.entity.JobPosting;
import com.example.intellimatch.entity.Skill;

import java.util.stream.Collectors;

public class JobPostingMapper {

    public static JobPosting toEntity(JobPostingRequest request) {
        if (request == null) {
            return null;
        }
        return JobPosting.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .minYearsOfExperience(request.getMinYearsOfExperience())
                .educationLevel(request.getEducationLevel())
                .active(true)
                .build();
    }
    public static JobPostingResponse toResponse(JobPosting jobPosting) {
        if (jobPosting == null) {
            return null;
        }
        return JobPostingResponse.builder()
                .id(jobPosting.getId())
                .title(jobPosting.getTitle())
                .description(jobPosting.getDescription())
                .minYearsOfExperience(jobPosting.getMinYearsOfExperience())
                .educationLevel(jobPosting.getEducationLevel())
                .active(jobPosting.isActive())
                .requiredSkills(jobPosting.getRequiredSkills().stream()
                        .map(Skill::getName)
                        .collect(Collectors.toSet()))
                .build();
    }
}
