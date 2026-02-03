package com.example.intellimatch.controller;

import com.example.intellimatch.dto.job.JobPostingRequest;
import com.example.intellimatch.dto.job.JobPostingResponse;
import com.example.intellimatch.enums.EducationLevel;
import com.example.intellimatch.service.JobPostingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JobPostingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JobPostingService jobPostingService;

    @Test
    @DisplayName("POST /api/jobPostings - Should create job posting successfully")
    @WithMockUser
    void shouldCreateJobPosting() throws Exception {
        JobPostingRequest request = new JobPostingRequest();
        request.setTitle("Backend Developer");
        request.setDescription("Java Spring Boot Developer needed with extensive experience.");
        request.setMinYearsOfExperience(3);
        request.setEducationLevel(EducationLevel.BACHELOR);
        request.setRequiredSkills(Set.of("Java", "Spring"));

        JobPostingResponse response = JobPostingResponse.builder()
                .id(1L)
                .title("Backend Developer")
                .active(true)
                .build();

        when(jobPostingService.createJobPosting(any(JobPostingRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/jobPostings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Backend Developer"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("POST /api/jobPostings - Should fail when validation constraints are not met")
    @WithMockUser
    void shouldFailValidation_WhenTitleIsTooShort() throws Exception {
        JobPostingRequest request = new JobPostingRequest();
        request.setTitle("Dev");
        request.setDescription("Valid description text here.");
        request.setMinYearsOfExperience(1);
        request.setEducationLevel(EducationLevel.BACHELOR);
        request.setRequiredSkills(Set.of("Java"));

        mockMvc.perform(post("/api/jobPostings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}