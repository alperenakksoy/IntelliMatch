package com.example.intellimatch.controller;

import com.example.intellimatch.dto.candidate.CandidateCreateRequest;
import com.example.intellimatch.dto.candidate.CandidateResponse;
import com.example.intellimatch.service.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
public class CandidateController {
    private final CandidateService candidateService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CandidateResponse createCandidate(@Valid @RequestBody CandidateCreateRequest request) {

        return candidateService.createCandidate(request);

    }

}
