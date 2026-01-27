package com.example.intellimatch.dto;

import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class CvExtractionResult {
    private String rawText;
    private String extractedEmail;
    private String extractedPhone;
    private Set<String> extractedSkills;
}