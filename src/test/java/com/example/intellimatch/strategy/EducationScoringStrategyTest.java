package com.example.intellimatch.strategy;

import com.example.intellimatch.entity.Candidate;
import com.example.intellimatch.entity.JobPosting;
import com.example.intellimatch.enums.EducationLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EducationScoringStrategyTest {

    private final EducationScoringStrategy strategy = new EducationScoringStrategy();

    @Test
    @DisplayName("Should return 100.0 when candidate meets education level")
    void shouldReturnFullScore_WhenEducationIsEqual() {
        Candidate candidate = Candidate.builder().educationLevel(EducationLevel.BACHELOR).build();
        JobPosting job = JobPosting.builder().educationLevel(EducationLevel.BACHELOR).build();

        double score = strategy.calculateScore(candidate, job);

        assertEquals(100.0, score);
    }

    @Test
    @DisplayName("Should return 100.0 when candidate exceeds education level")
    void shouldReturnFullScore_WhenEducationIsHigher() {
        Candidate candidate = Candidate.builder().educationLevel(EducationLevel.MASTER).build();
        JobPosting job = JobPosting.builder().educationLevel(EducationLevel.BACHELOR).build();

        double score = strategy.calculateScore(candidate, job);

        assertEquals(100.0, score);
    }

    @Test
    @DisplayName("Should return 50.0 when candidate is below education level")
    void shouldReturnHalfScore_WhenEducationIsLower() {
        Candidate candidate = Candidate.builder().educationLevel(EducationLevel.HIGH_SCHOOL).build();
        JobPosting job = JobPosting.builder().educationLevel(EducationLevel.BACHELOR).build();

        double score = strategy.calculateScore(candidate, job);

        assertEquals(50.0, score);
    }
}