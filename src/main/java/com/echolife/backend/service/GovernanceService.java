package com.echolife.backend.service;

import com.echolife.backend.entity.ConsentType;
import com.echolife.backend.entity.Persona;
import com.echolife.backend.entity.ResponseMode;
import com.echolife.backend.repository.ConsentRepository;
import com.echolife.backend.repository.PersonaRepository;
import org.springframework.stereotype.Service;

@Service
public class GovernanceService {

    private final ConsentRepository consentRepository;
    private final PersonaRepository personaRepository;
    private final SafetyService safetyService;

    public GovernanceService(ConsentRepository consentRepository,
                             PersonaRepository personaRepository,
                             SafetyService safetyService) {
        this.consentRepository = consentRepository;
        this.personaRepository = personaRepository;
        this.safetyService = safetyService;
    }

    public void validateAiExecution(Long userId, Long personaId, ResponseMode mode, String contentToProcess) {
        boolean hasConsent = consentRepository
                .findTopByUserIdAndConsentTypeOrderByGrantedAtDesc(userId, ConsentType.AI_DATA_PROCESSING)
                .map(c -> c.isGranted() && c.getRevokedAt() == null)
                .orElse(false);

        if (!hasConsent) {
            throw new SecurityException("User has not granted active consent for AI Data Processing.");
        }

        if (personaId != null) {
            Persona persona = personaRepository.findById(personaId)
                    .orElseThrow(() -> new IllegalArgumentException("Persona not found with ID: " + personaId));

            if (!persona.isActive()) {
                throw new IllegalStateException("The selected persona is currently inactive.");
            }

            if (!persona.getAllowedModes().contains(mode)) {
                throw new IllegalArgumentException("Mode " + mode + " is not permitted for persona: " + persona.getName());
            }
        }

        SafetyService.SafetyResult safetyResult = safetyService.evaluateContent(contentToProcess);
        if (!safetyResult.isAllowed()) {
            throw new IllegalArgumentException("Safety violation: " + safetyResult.message());
        }
    }
}
