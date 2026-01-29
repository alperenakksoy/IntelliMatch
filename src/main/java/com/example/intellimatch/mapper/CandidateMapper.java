package com.example.intellimatch.mapper;

import com.example.intellimatch.dto.candidate.CandidateCreateRequest;
import com.example.intellimatch.dto.candidate.CandidateResponse;
import com.example.intellimatch.entity.Candidate;

public class CandidateMapper {

    public static Candidate toEntity(CandidateCreateRequest request) {
        if (request == null) {
            return null;
        }
        return Candidate.builder()
                .fullName(request.getCandidateFullName())
                .email(request.getCandidateEmail())
                .phone(request.getCandidatePhone())
                .yearsOfExperience(request.getCandidateYearsExperience())
                .educationLevel(request.getCandidateEducationLevel())
                .build();
    }

    public static CandidateResponse toResponse(Candidate candidate) {
        if (candidate == null) {
            return null;
        }
        return CandidateResponse.builder()
                .candidateId(candidate.getId())
                .candidateName(candidate.getFullName())
                .email(candidate.getEmail())
                .build();
    }
}
