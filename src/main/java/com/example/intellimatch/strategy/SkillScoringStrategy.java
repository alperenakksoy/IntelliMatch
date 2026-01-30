package com.example.intellimatch.strategy;

import com.example.intellimatch.entity.Candidate;
import com.example.intellimatch.entity.JobPosting;
import com.example.intellimatch.entity.Skill;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SkillScoringStrategy implements ScoringStrategy {
    @Override
    public double calculateScore(Candidate candidate, JobPosting job) {

        Set<String> requiredSkills = job.getRequiredSkills().stream()
                .map(Skill::getName)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        if(requiredSkills.isEmpty()) {
            return 100.0;
        }

        Set<String> candidateSkills = candidate.getSkills().stream()
                .map(Skill::getName)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        long matchCount = requiredSkills.stream()
                .filter(candidateSkills::contains)
                .count();

        return ((double) matchCount) / requiredSkills.size() * 100;
    }
}
