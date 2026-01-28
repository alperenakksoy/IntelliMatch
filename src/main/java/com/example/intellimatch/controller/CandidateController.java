package com.example.intellimatch.controller;

import com.example.intellimatch.dto.candidate.CandidateCreateRequest;
import com.example.intellimatch.dto.candidate.CandidateResponse;
import com.example.intellimatch.dto.cv.CvExtractionResult;
import com.example.intellimatch.service.CandidateService;
import com.example.intellimatch.service.CvParsingService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
public class CandidateController {
    private final CandidateService candidateService;
    private final CvParsingService cvParsingService;

    @PostMapping
    public ResponseEntity<@NonNull CandidateResponse> createCandidate(
            @Valid @RequestBody CandidateCreateRequest request) {

        CandidateResponse response = candidateService.createCandidate(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/upload-cv")
    public ResponseEntity<@NonNull String> uploadCv(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        CvExtractionResult extractionResult = cvParsingService.extractCv(file);
        candidateService.updateCandidateWithCv(id, extractionResult, file.getOriginalFilename());
        return ResponseEntity.ok("CV processed successfully");
    }

}
