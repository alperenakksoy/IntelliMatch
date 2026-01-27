package com.example.intellimatch.dto.candidate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CandidateResponse {

    private Long candidateId;
    private String candidateName;
    private String email;
}
