package com.example.intellimatch.service;

import com.example.intellimatch.dto.matchResult.MatchResultResponse;
import com.example.intellimatch.entity.Candidate;
import com.example.intellimatch.entity.JobPosting;
import com.example.intellimatch.entity.MatchResult;
import com.example.intellimatch.mapper.MatchResultMapper;
import com.example.intellimatch.repository.CandidateRepository;
import com.example.intellimatch.repository.JobPostingRepository;
import com.example.intellimatch.repository.MatchResultRepository;
import com.example.intellimatch.strategy.EducationScoringStrategy;
import com.example.intellimatch.strategy.ExperienceScoringStrategy;
import com.example.intellimatch.strategy.SkillScoringStrategy;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MatchingService {

    private final CandidateRepository candidateRepository;
    private final JobPostingRepository jobPostingRepository;
    private final MatchResultRepository matchResultRepository;
    private final SkillScoringStrategy skillScoringStrategy;
    private final EducationScoringStrategy educationScoringStrategy;
    private final ExperienceScoringStrategy experienceScoringStrategy;
    private final MatchingService self;

    public MatchingService(CandidateRepository candidateRepository,
                           JobPostingRepository jobPostingRepository,
                           MatchResultRepository matchResultRepository,
                           SkillScoringStrategy skillScoringStrategy,
                           EducationScoringStrategy educationScoringStrategy,
                           ExperienceScoringStrategy experienceScoringStrategy,
                           @Lazy MatchingService self) { //
        this.candidateRepository = candidateRepository;
        this.jobPostingRepository = jobPostingRepository;
        this.matchResultRepository = matchResultRepository;
        this.skillScoringStrategy = skillScoringStrategy;
        this.educationScoringStrategy = educationScoringStrategy;
        this.experienceScoringStrategy = experienceScoringStrategy;
        this.self = self;
    }

    @Transactional
    public MatchResult matchCandidateToJob(Long candidateId, Long jobId) {
        // checking if candidate and job posting exist
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found: " + candidateId));
        JobPosting jobPosting = jobPostingRepository.findById(jobId)
                .orElseThrow(() -> new EntityNotFoundException("Job posting not found: " + jobId));

        // no duplication of matching results
        Optional<MatchResult> existingMatch = matchResultRepository.findByCandidateIdAndJobPostingId(candidateId, jobId);
        if (existingMatch.isPresent()) {
            return existingMatch.get();
        }

        double skillScore = skillScoringStrategy.calculateScore(candidate, jobPosting);
        double experienceScore = experienceScoringStrategy.calculateScore(candidate, jobPosting);
        double educationScore = educationScoringStrategy.calculateScore(candidate, jobPosting);

        double finalScore = skillScore * 0.5 + experienceScore * 0.3 + educationScore * 0.2;

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

    @Transactional
    public List<MatchResultResponse> rankCandidatesToJob(Long jobId) {
        if (!jobPostingRepository.existsById(jobId)) {
            throw new EntityNotFoundException("Job posting not found: " + jobId);
        }

        List<Candidate> allCandidates = candidateRepository.findAll();

        allCandidates.forEach(candidate -> self.matchCandidateToJob(candidate.getId(), jobId));

        return matchResultRepository.findByJobPostingIdOrderByFinalScoreDesc(jobId).stream()
                .map(MatchResultMapper::toResponse)
                .toList();
    }
}