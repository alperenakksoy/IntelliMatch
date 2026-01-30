package com.example.intellimatch.strategy;

import com.example.intellimatch.entity.Candidate;
import com.example.intellimatch.entity.JobPosting;
import org.springframework.stereotype.Component;

@Component
public class ExperienceScoringStrategy implements ScoringStrategy {

    @Override
    public double calculateScore(Candidate candidate, JobPosting job) {
        int minExp = job.getMinYearsOfExperience();
        int candidateExp = candidate.getYearsOfExperience();

        if(minExp == 0) {
            return 100.0;
        }

        if(candidateExp >= minExp) {
            int extraYears = candidateExp - minExp;
            double bonus = extraYears * 5.0;
            return Math.min(100.0 + bonus, 120.0);
        } else {
            return ((double) candidateExp / minExp) * 100.0;
        }
    }

}
