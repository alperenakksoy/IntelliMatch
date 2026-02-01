package com.example.intellimatch.repository;

import com.example.intellimatch.entity.JobPosting;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobPostingRepository extends JpaRepository<@NonNull JobPosting,@NonNull Long> {
    List<JobPosting> findByActiveTrue();
}
