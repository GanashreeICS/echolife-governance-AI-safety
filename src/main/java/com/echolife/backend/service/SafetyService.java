package com.echolife.backend.service;

import com.echolife.backend.entity.RiskLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SafetyService {

    private static final List<String> BLOCKED_PATTERNS = List.of(
            "kill myself", "suicide", "hurt others", "make a bomb", "attack"
    );

    public record SafetyResult(RiskLevel riskLevel, String message, boolean isAllowed) {}

    public SafetyResult evaluateContent(String text) {
        if (text == null || text.isBlank()) {
            return new SafetyResult(RiskLevel.SAFE, "Content is empty.", true);
        }

        String normalized = text.toLowerCase();
        for (String pattern : BLOCKED_PATTERNS) {
            if (normalized.contains(pattern)) {
                return new SafetyResult(
                        RiskLevel.BLOCKED,
                        "Input contains flagged safety concerns. Support resources are recommended.",
                        false
                );
            }
        }

        return new SafetyResult(RiskLevel.SAFE, "Content passed basic safety checks.", true);
    }
}