package com.example.intellimatch.repository;

import com.example.intellimatch.entity.Skill;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<@NonNull Skill,@NonNull Long> {
    Optional<Skill> findByNameIgnoreCase(String name);
}
