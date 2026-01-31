package com.example.intellimatch.controller;

import com.example.intellimatch.dto.matchResult.MatchResultResponse;
import com.example.intellimatch.service.MatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchingService matchingService;

    @PostMapping("/rank/{jobId}")
    public ResponseEntity<List<MatchResultResponse>> rankCandidatesToJob(@PathVariable Long jobId)
    {
       List<MatchResultResponse> matchResultResponses = matchingService.rankCandidatesToJob(jobId);
       return ResponseEntity.ok(matchResultResponses);
    }


}
