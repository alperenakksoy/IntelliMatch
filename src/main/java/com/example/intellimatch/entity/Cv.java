package com.example.intellimatch.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cvs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    private String rawText;

    @Column(nullable = false)
    private String fileName;

    @OneToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

}
