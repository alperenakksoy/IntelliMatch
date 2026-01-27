package com.example.intellimatch.dto.candidate;

import com.example.intellimatch.enums.EducationLevel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateCreateRequest {

    @NotBlank
    private String candidateFullName;

    @NotBlank
    @Email
    private String candidateEmail;

    @NotBlank
    private String candidatePhone;

    @Min(0)
    private Integer candidateYearsExperience;

    @NotNull
    private EducationLevel candidateEducationLevel;

}
