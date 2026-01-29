package com.example.intellimatch.dto.job;

import com.example.intellimatch.enums.EducationLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class JobPostingResponse {

    private Long id;
    private String title;
    private String description;

    private int minYearsOfExperience;
    private EducationLevel educationLevel;

    private boolean active;

    private Set<String> requiredSkills;
}
