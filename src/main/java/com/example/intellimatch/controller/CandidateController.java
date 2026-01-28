package com.example.intellimatch.controller;

import com.example.intellimatch.dto.candidate.CandidateCreateRequest;
import com.example.intellimatch.dto.candidate.CandidateResponse;
import com.example.intellimatch.service.CandidateService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
public class CandidateController {
    private final CandidateService candidateService;

    @PostMapping
    public ResponseEntity<@NonNull CandidateResponse> createCandidate(
            @Valid @RequestBody CandidateCreateRequest request) {

        CandidateResponse response = candidateService.createCandidate(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

}
