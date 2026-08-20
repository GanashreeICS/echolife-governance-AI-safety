package com.echolife.backend.repository;

import com.echolife.backend.entity.Prompt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromptRepository extends JpaRepository<Prompt, Long> {
    // Find all prompts filtered by category
    List<Prompt> findByCategoryIgnoreCase(String category);

    // Find all prompts that are marked active
    List<Prompt> findByActiveTrue();
}