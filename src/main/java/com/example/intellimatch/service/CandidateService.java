package com.example.intellimatch.service;

import com.example.intellimatch.dto.candidate.CandidateCreateRequest;
import com.example.intellimatch.dto.candidate.CandidateResponse;
import com.example.intellimatch.entity.Candidate;
import com.example.intellimatch.mapper.CandidateMapper;
import com.example.intellimatch.repository.CandidateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;

    @Transactional
    public CandidateResponse createCandidate(CandidateCreateRequest request) {
        if (candidateRepository.existsByEmail(request.getCandidateEmail())) {
            throw new RuntimeException("Email already registered");
        }
        Candidate candidate = CandidateMapper.toEntity(request);

        Candidate saveCandidate = candidateRepository.save(candidate);

        return CandidateMapper.toResponse(saveCandidate);
    }
}
