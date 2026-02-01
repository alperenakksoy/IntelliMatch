package com.example.intellimatch.repository;

import com.example.intellimatch.entity.Candidate;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<@NonNull Candidate,@NonNull Long> {
    boolean existsByEmail(String email);
}
