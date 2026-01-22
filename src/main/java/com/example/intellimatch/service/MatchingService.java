package com.example.intellimatch.service;

import com.example.intellimatch.entity.Candidate;
import com.example.intellimatch.entity.JobPosting;
import com.example.intellimatch.entity.MatchResult;
import com.example.intellimatch.entity.Skill;
import com.example.intellimatch.enums.EducationLevel;
import com.example.intellimatch.repository.CandidateRepository;
import com.example.intellimatch.repository.JobPostingRepository;
import com.example.intellimatch.repository.MatchResultRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final CandidateRepository candidateRepository;
    private final JobPostingRepository jobPostingRepository;
    private final MatchResultRepository matchResultRepository;

    @Transactional
    public MatchResult matchCandidateToJob(Long candidateId, Long JobId) {

        // checking if candidate and job posting exist
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found: " + candidateId));

        JobPosting jobPosting = jobPostingRepository.findById(JobId)
                .orElseThrow(() -> new RuntimeException("Job posting not found: " + JobId));

        // no duplication of matching results
        Optional<MatchResult> existingMatch = matchResultRepository.findByCandidateIdAndJobPostingId(candidateId, JobId);
        if (existingMatch.isPresent()) {
            return existingMatch.get();
        }

        double skillScore = calculateSkillScore(candidate, jobPosting);
        double experienceScore = calculateExperienceScore(candidate, jobPosting);
        double educationScore = calculateEducationScore(candidate, jobPosting);

        double finalScore = (skillScore * 0.5) + (experienceScore * 0.3) + (educationScore * 0.2);

        MatchResult matchResult = MatchResult.builder()
                .candidate(candidate)
                .jobPosting(jobPosting)
                .skillScore(skillScore)
                .experienceScore(experienceScore)
                .educationScore(educationScore)
                .finalScore(finalScore)
                .build();

        return matchResultRepository.save(matchResult);

    }

    private double calculateSkillScore(Candidate candidate, JobPosting job) {

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

    private double calculateExperienceScore(Candidate candidate, JobPosting job) {
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

    private double calculateEducationScore(Candidate candidate, JobPosting job) {
        EducationLevel candidateLvl = candidate.getEducationLevel();
        EducationLevel jobLvl = job.getEducationLevel();

        if(candidateLvl.ordinal() >= jobLvl.ordinal()) {
            return 100.0;
        } else{
            return 50.0;
        }
    }


}