package com.example.intellimatch.repository;

import com.example.intellimatch.entity.Cv;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvRepository extends JpaRepository<@NonNull Cv,@NonNull Long> {
}
