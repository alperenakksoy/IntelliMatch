package com.example.intellimatch.strategy;

import com.example.intellimatch.entity.Candidate;
import com.example.intellimatch.entity.JobPosting;
import com.example.intellimatch.entity.Skill;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SkillScoringStrategyTest {

    private final SkillScoringStrategy strategy = new SkillScoringStrategy();

    @Test
    @DisplayName("Should return 100.0 when job has no required skills")
    void shouldReturnFullScore_WhenNoSkillsRequired() {
        Candidate candidate = Candidate.builder().skills(Collections.emptySet()).build();
        JobPosting job = JobPosting.builder().requiredSkills(Collections.emptySet()).build();

        double score = strategy.calculateScore(candidate, job);

        assertEquals(100.0, score);
    }

    @Test
    @DisplayName("Should calculate percentage based on matched skills")
    void shouldCalculateCorrectMatchPercentage() {
        Skill java = Skill.builder().name("Java").build();
        Skill python = Skill.builder().name("Python").build();
        Skill sql = Skill.builder().name("SQL").build();

        JobPosting job = JobPosting.builder()
                .requiredSkills(Set.of(java, python))
                .build();

        Candidate candidate = Candidate.builder()
                .skills(Set.of(java, sql))
                .build();

        double score = strategy.calculateScore(candidate, job);

        assertEquals(50.0, score);
    }

    @Test
    @DisplayName("Should match skills regardless of case sensitivity")
    void shouldMatchSkillsCaseInsensitive() {
        Skill reqSkill = Skill.builder().name("JAVA").build();
        Skill candSkill = Skill.builder().name("java").build();

        JobPosting job = JobPosting.builder().requiredSkills(Set.of(reqSkill)).build();
        Candidate candidate = Candidate.builder().skills(Set.of(candSkill)).build();

        double score = strategy.calculateScore(candidate, job);

        assertEquals(100.0, score);
    }
}