package com.example.intellimatch.controller;

import com.example.intellimatch.dto.job.JobPostingRequest;
import com.example.intellimatch.dto.job.JobPostingResponse;
import com.example.intellimatch.service.JobPostingService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jobPostings")
@RequiredArgsConstructor
public class JobPostingController {
    private final JobPostingService jobPostingService;

    @PostMapping
    public ResponseEntity<@NonNull JobPostingResponse> createJobPosting(
            @Valid @RequestBody JobPostingRequest jobPostingRequest
    ){
        JobPostingResponse jobPostingResponse = jobPostingService.createJobPosting(jobPostingRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobPostingResponse);
    }

    @GetMapping
    public ResponseEntity<List<@NonNull JobPostingResponse>> getActiveJobPostings(){
        List<JobPostingResponse> responses = jobPostingService.getActiveJobPostings();
        return ResponseEntity.ok(responses);
    }
}
