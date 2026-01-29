package com.example.intellimatch.service;

import com.example.intellimatch.dto.job.JobPostingRequest;
import com.example.intellimatch.dto.job.JobPostingResponse;
import com.example.intellimatch.entity.JobPosting;
import com.example.intellimatch.entity.Skill;
import com.example.intellimatch.mapper.JobPostingMapper;
import com.example.intellimatch.repository.JobPostingRepository;
import com.example.intellimatch.repository.SkillRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobPostingService {
    private final JobPostingRepository jobPostingRepository;
    private final SkillRepository skillRepository;


    @Transactional
    public JobPostingResponse createJobPosting(JobPostingRequest request) {

        JobPosting jobPosting = JobPostingMapper.toEntity(request);

        if (request.getRequiredSkills() != null && !request.getRequiredSkills().isEmpty()) {
            Set<Skill> skills = request.getRequiredSkills().stream()
                    .map(skillName -> {
                        String normalizedName = skillName.trim().toLowerCase();

                        return skillRepository.findByNameIgnoreCase(normalizedName)
                                .orElseGet(() -> skillRepository.save(
                                        Skill.builder().name(normalizedName).build()
                                ));
                    })
                    .collect(Collectors.toSet());

            jobPosting.setRequiredSkills(skills);
    }
        JobPosting savedJobPosting = jobPostingRepository.save(jobPosting);

        return JobPostingMapper.toResponse(savedJobPosting);

        }

    public List<JobPostingResponse> getActiveJobPostings() {
        return jobPostingRepository.findByActiveTrue().stream()
                .map(JobPostingMapper::toResponse)
                .collect(Collectors.toList());
    }
}
