package com.example.intellimatch.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "match_results", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"candidate_id", "job_posting_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class MatchResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double skillScore;

    @Column(nullable = false)
    private double educationScore;

    @Column(nullable = false)
    private double experienceScore;

    @Column(nullable = false)
    private double finalScore;

    @CreatedDate
    @Column(nullable = false,  updatable = false)
    private LocalDateTime calculatedAt;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;

}
