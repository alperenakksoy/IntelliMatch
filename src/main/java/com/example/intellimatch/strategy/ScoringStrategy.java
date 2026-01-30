package com.example.intellimatch.strategy;

import com.example.intellimatch.entity.Candidate;
import com.example.intellimatch.entity.JobPosting;

public interface ScoringStrategy {
    double calculateScore(Candidate candidate, JobPosting Job);
}
