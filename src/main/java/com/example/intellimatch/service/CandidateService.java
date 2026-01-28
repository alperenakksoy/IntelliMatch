package com.example.intellimatch.service;

import com.example.intellimatch.dto.candidate.CandidateCreateRequest;
import com.example.intellimatch.dto.candidate.CandidateResponse;
import com.example.intellimatch.dto.cv.CvExtractionResult;
import com.example.intellimatch.entity.Candidate;
import com.example.intellimatch.entity.Cv;
import com.example.intellimatch.entity.Skill;
import com.example.intellimatch.mapper.CandidateMapper;
import com.example.intellimatch.repository.CandidateRepository;
import com.example.intellimatch.repository.CvRepository;
import com.example.intellimatch.repository.SkillRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final CvRepository cvRepository;
    private final SkillRepository skillRepository;

    @Transactional
    public CandidateResponse createCandidate(CandidateCreateRequest request) {
        if (candidateRepository.existsByEmail(request.getCandidateEmail())) {
            throw new RuntimeException("Email already registered");
        }
        Candidate candidate = CandidateMapper.toEntity(request);

        Candidate saveCandidate = candidateRepository.save(candidate);

        return CandidateMapper.toResponse(saveCandidate);
    }

    public void updateCandidateWithCv(Long candidateId, CvExtractionResult result, String fileName) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        Cv cv = Cv.builder()
                .rawText(result.getRawText())
                .fileName(fileName)
                .candidate(candidate)
                .build();

        cvRepository.save(cv);

        Set<Skill> skills = result.getExtractedSkills().stream()
                .map(skillName -> {
                    String normalized = skillName.trim().toLowerCase();
                    return skillRepository.findByNameIgnoreCase(normalized)
                            .orElseGet(() -> skillRepository.save(
                                    Skill.builder().name(normalized).build())
                            );
                })
                .collect(Collectors.toSet());

        candidate.getSkills().clear();
        candidate.getSkills().addAll(skills);
        candidateRepository.save(candidate);
    }
}
