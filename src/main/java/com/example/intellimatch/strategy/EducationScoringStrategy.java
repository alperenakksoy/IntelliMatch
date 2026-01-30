package com.example.intellimatch.strategy;

import com.example.intellimatch.entity.Candidate;
import com.example.intellimatch.entity.JobPosting;
import com.example.intellimatch.enums.EducationLevel;
import org.springframework.stereotype.Component;

@Component
public class EducationScoringStrategy implements ScoringStrategy {

    @Override
    public double calculateScore(Candidate candidate, JobPosting job) {

        EducationLevel candidateLvl = candidate.getEducationLevel();
        EducationLevel jobLvl = job.getEducationLevel();

        if(candidateLvl.ordinal() >= jobLvl.ordinal()) {
            return 100.0;
        } else{
            return 50.0;
        }
    }
}
