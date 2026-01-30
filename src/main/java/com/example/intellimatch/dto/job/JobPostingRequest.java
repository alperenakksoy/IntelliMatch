package com.example.intellimatch.dto.job;

import com.example.intellimatch.enums.EducationLevel;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class JobPostingRequest {

    @NotBlank(message = "Title must not be blank")
    @Size(min=5, max=200,message = "Must be between 5 and 200 char")
    private String title;

    @NotBlank(message = "Description must not be blank")
    @Size(min = 20, max = 5000, message = "Must be between 20 and 5000 char")
    private String description;

    @Min(value = 0,message = "Years of experience must be minimum 0" )
    @Max(value = 70, message = "Years of experience must be maximum 70 years")
    private Integer minYearsOfExperience;

    @NotNull(message = "Education level can not be empty")
    private EducationLevel educationLevel;

    @NotEmpty(message = "You have to at add at least a skill")
    private Set<@NotBlank(message = "Skill name can not be blank") String> requiredSkills;
}
