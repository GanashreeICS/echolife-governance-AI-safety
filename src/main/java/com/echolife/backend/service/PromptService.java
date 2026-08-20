package com.echolife.backend.service;

import com.echolife.backend.entity.Prompt;
import com.echolife.backend.repository.PromptRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromptService {

    private final PromptRepository promptRepository;

    public PromptService(PromptRepository promptRepository) {
        this.promptRepository = promptRepository;
    }

    // 1. Create a new prompt
    public Prompt createPrompt(Prompt prompt) {
        return promptRepository.save(prompt);
    }

    // 2. Get all prompts
    public List<Prompt> getAllPrompts() {
        return promptRepository.findAll();
    }

    // 3. Get active prompts only
    public List<Prompt> getActivePrompts() {
        return promptRepository.findByActiveTrue();
    }

    // 4. Get prompts by category
    public List<Prompt> getPromptsByCategory(String category) {
        return promptRepository.findByCategoryIgnoreCase(category);
    }

    // 5. Delete a prompt by ID
    public boolean deletePrompt(Long id) {
        if (promptRepository.existsById(id)) {
            promptRepository.deleteById(id);
            return true;
        }
        return false;
    }
}