package com.example.intellimatch.service;

import com.example.intellimatch.entity.Candidate;
import com.example.intellimatch.entity.JobPosting;
import com.example.intellimatch.entity.MatchResult;
import com.example.intellimatch.repository.CandidateRepository;
import com.example.intellimatch.repository.JobPostingRepository;
import com.example.intellimatch.repository.MatchResultRepository;
import com.example.intellimatch.strategy.EducationScoringStrategy;
import com.example.intellimatch.strategy.ExperienceScoringStrategy;
import com.example.intellimatch.strategy.SkillScoringStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchingServiceTest {

    @Mock private CandidateRepository candidateRepository;
    @Mock private JobPostingRepository jobPostingRepository;
    @Mock private MatchResultRepository matchResultRepository;

    @Mock private SkillScoringStrategy skillStrategy;
    @Mock private ExperienceScoringStrategy experienceStrategy;
    @Mock private EducationScoringStrategy educationStrategy;

    @InjectMocks
    private MatchingService matchingService;

    @Test
    @DisplayName("Should calculate weighted final score correctly")
    void shouldCalculateWeightedScore() {
        Long candidateId = 1L;
        Long jobId = 2L;
        Candidate candidate = new Candidate();
        JobPosting job = new JobPosting();

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));
        when(jobPostingRepository.findById(jobId)).thenReturn(Optional.of(job));
        when(matchResultRepository.findByCandidateIdAndJobPostingId(candidateId, jobId)).thenReturn(Optional.empty());

        when(skillStrategy.calculateScore(candidate, job)).thenReturn(100.0);
        when(experienceStrategy.calculateScore(candidate, job)).thenReturn(80.0);
        when(educationStrategy.calculateScore(candidate, job)).thenReturn(50.0);

        when(matchResultRepository.save(any(MatchResult.class))).thenAnswer(i -> i.getArguments()[0]);

        MatchResult result = matchingService.matchCandidateToJob(candidateId, jobId);

        assertNotNull(result);
        assertEquals(100.0, result.getSkillScore());
        assertEquals(80.0, result.getExperienceScore());
        assertEquals(50.0, result.getEducationScore());
        assertEquals(84.0, result.getFinalScore());

        verify(matchResultRepository).save(any(MatchResult.class));
    }
}