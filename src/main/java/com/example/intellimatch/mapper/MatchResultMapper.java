package com.example.intellimatch.mapper;

import com.example.intellimatch.dto.matchResult.MatchResultResponse;
import com.example.intellimatch.entity.MatchResult;

public class MatchResultMapper {

    public static MatchResultResponse toResponse(MatchResult matchResult) {
        if (matchResult == null) {
            return null;
        }
        if (matchResult.getCandidate() == null || matchResult.getJobPosting() == null) {
            throw new IllegalArgumentException("Candidate or Job posting can't be null");
        }
        return MatchResultResponse.builder()
                .matchId(matchResult.getId())
                .candidateId(matchResult.getCandidate().getId())
                .candidateName(matchResult.getCandidate().getFullName())
                .jobPostingId(matchResult.getJobPosting().getId())
                .jobTitle(matchResult.getJobPosting().getTitle())
                .skillScore(matchResult.getSkillScore())
                .experienceScore(matchResult.getExperienceScore())
                .educationScore(matchResult.getEducationScore())
                .finalScore(matchResult.getFinalScore())
                .calculatedAt(matchResult.getCalculatedAt())
                .build();
    }
}
