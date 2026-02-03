package com.example.intellimatch.strategy;

import com.example.intellimatch.entity.Candidate;
import com.example.intellimatch.entity.JobPosting;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExperienceScoringStrategyTest {

    private final ExperienceScoringStrategy strategy = new ExperienceScoringStrategy();

    @Test
    @DisplayName("Should return 100.0 when no minimum experience is required")
    void shouldReturnFullScore_WhenMinExperienceIsZero() {
        Candidate candidate = Candidate.builder().yearsOfExperience(2).build();
        JobPosting job = JobPosting.builder().minYearsOfExperience(0).build();

        double score = strategy.calculateScore(candidate, job);

        assertEquals(100.0, score);
    }

    @Test
    @DisplayName("Should return proportional score when candidate has less experience than required")
    void shouldReturnProportionalScore_WhenExperienceIsInsufficient() {
        Candidate candidate = Candidate.builder().yearsOfExperience(5).build();
        JobPosting job = JobPosting.builder().minYearsOfExperience(10).build();

        double score = strategy.calculateScore(candidate, job);

        assertEquals(50.0, score);
    }

    @Test
    @DisplayName("Should calculate bonus points when candidate exceeds required experience")
    void shouldCalculateBonus_WhenExperienceExceedsRequirement() {
        Candidate candidate = Candidate.builder().yearsOfExperience(5).build();
        JobPosting job = JobPosting.builder().minYearsOfExperience(3).build();

        double score = strategy.calculateScore(candidate, job);

        assertEquals(110.0, score);
    }

    @Test
    @DisplayName("Should cap the maximum score at 120.0")
    void shouldCapScoreAtMaxLimit() {
        Candidate candidate = Candidate.builder().yearsOfExperience(20).build();
        JobPosting job = JobPosting.builder().minYearsOfExperience(1).build();

        double score = strategy.calculateScore(candidate, job);

        assertEquals(120.0, score);
    }
}