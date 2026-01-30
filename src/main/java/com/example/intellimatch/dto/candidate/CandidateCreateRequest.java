package com.example.intellimatch.dto.candidate;

import com.example.intellimatch.enums.EducationLevel;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateCreateRequest {

    @NotBlank(message = "Full name cannot be blank")
    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String candidateFullName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    private String candidateEmail;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^[0-9]{10,}$", message = "Phone number must contain at least 10 digits")
    private String candidatePhone;

    @NotNull(message = "Years of experience cannot be null")
    @Min(value = 0, message = "Years of experience must be 0 or greater")
    @Max(value = 70, message = "Years of experience must be less than 70")
    private Integer candidateYearsExperience;

    @NotNull(message = "Education level cannot be null")
    private EducationLevel candidateEducationLevel;
}