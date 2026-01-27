package com.example.intellimatch.controller;

import com.example.intellimatch.repository.CandidateRepository;
import com.example.intellimatch.service.CvParsingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
public class CandidateController {
    private final CandidateRepository candidateRepository;
    private final CvParsingService cvParsingService;



}
