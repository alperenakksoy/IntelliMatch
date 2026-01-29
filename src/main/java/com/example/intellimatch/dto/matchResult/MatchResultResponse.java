package com.example.intellimatch.dto.matchResult;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MatchResultResponse {

    private Long matchId;

    private Long candidateId;
    private String candidateName;

    private Long jobPostingId;
    private String jobTitle;

    private double skillScore;
    private double experienceScore;
    private double educationScore;
    private double finalScore;

    private LocalDateTime calculatedAt;
}
