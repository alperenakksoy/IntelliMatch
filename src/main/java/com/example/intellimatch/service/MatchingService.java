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
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MatchingService {

    private final CandidateRepository candidateRepository;
    private final JobPostingRepository jobPostingRepository;
    private final MatchResultRepository matchResultRepository;

    private final SkillScoringStrategy skillScoringStrategy;
    private final EducationScoringStrategy educationScoringStrategy;
    private final ExperienceScoringStrategy experienceScoringStrategy;

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

        double skillScore = skillScoringStrategy.calculateScore(candidate, jobPosting);
        double experienceScore = experienceScoringStrategy.calculateScore(candidate, jobPosting);
        double educationScore = educationScoringStrategy.calculateScore(candidate, jobPosting);

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

    @Transactional
    public List<MatchResultResponse> rankCandidatesToJob(Long jobId) {

        if(!jobPostingRepository.existsById(jobId)) {
            throw new RuntimeException("Job posting not found: " + jobId);
        }

        List<Candidate> allCandidates = candidateRepository.findAll();

        allCandidates.forEach(candidate -> matchCandidateToJob(candidate.getId(), jobId));

        return matchResultRepository.findByJobPostingIdOrderByFinalScoreDesc(jobId).stream()
                .map(MatchResultMapper::toResponse)
                .collect(Collectors.toList());


    }
}