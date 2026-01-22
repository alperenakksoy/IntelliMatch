package com.example.intellimatch.repository;

import com.example.intellimatch.entity.MatchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchResultRepository extends JpaRepository<MatchResult, Long> {
    Optional<MatchResult> findByCandidateIdAndJobPostingId(Long candidateId, Long jobPostingId);

    List<MatchResult> findByJobPostingIdOrderByFinalScoreDesc(Long jobPostingId);
}
