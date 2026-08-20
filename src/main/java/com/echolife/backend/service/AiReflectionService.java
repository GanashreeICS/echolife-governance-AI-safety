package com.echolife.backend.service;

import com.echolife.backend.entity.Memory;
import org.springframework.stereotype.Service;

@Service
public class AiReflectionService {

    /**
     * Analyzes memory description and generates an AI summary and emotional tone.
     */
    public Memory generateReflection(Memory memory) {
        String description = memory.getDescription();

        if (description == null || description.isBlank()) {
            memory.setAiReflectionSummary("A brief life moment preserved on EchoLife.");
            memory.setEmotionalTone("Reflective");
            return memory;
        }

        String lower = description.toLowerCase();

        // Simulated intelligent sentiment and theme extraction
        if (lower.contains("graduat") || lower.contains("degree") || lower.contains("career") || lower.contains("work")) {
            memory.setEmotionalTone("Accomplished & Ambitious");
            memory.setAiReflectionSummary("A milestone highlighting professional dedication, growth, and personal achievement.");
        } else if (lower.contains("family") || lower.contains("mom") || lower.contains("dad") || lower.contains("child") || lower.contains("grandparent")) {
            memory.setEmotionalTone("Warm & Nostalgic");
            memory.setAiReflectionSummary("A deep family memory capturing shared bonds and lasting generational connections.");
        } else if (lower.contains("love") || lower.contains("wedding") || lower.contains("partner") || lower.contains("spouse")) {
            memory.setEmotionalTone("Loving & Cherished");
            memory.setAiReflectionSummary("A meaningful chapter centered on partnership, shared commitments, and deep affection.");
        } else {
            memory.setEmotionalTone("Thoughtful & Inspiring");
            memory.setAiReflectionSummary("A personal reflection documenting life experience and legacy perspective.");
        }

        return memory;
    }
}