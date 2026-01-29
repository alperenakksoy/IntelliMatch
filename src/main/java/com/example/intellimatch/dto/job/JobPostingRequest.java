package com.example.intellimatch.dto.job;

import com.example.intellimatch.enums.EducationLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class JobPostingRequest {

    private String title;
    private String description;
    private int minYearsOfExperience;
    private EducationLevel educationLevel;
    private Set<String> requiredSkills;
}
