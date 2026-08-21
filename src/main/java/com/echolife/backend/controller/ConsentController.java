package com.echolife.backend.controller;

import com.echolife.backend.entity.Consent;
import com.echolife.backend.entity.ConsentType;
import com.echolife.backend.repository.ConsentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/consents")
public class ConsentController {

    private final ConsentRepository consentRepository;

    public ConsentController(ConsentRepository consentRepository) {
        this.consentRepository = consentRepository;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Consent>> getUserConsents(@PathVariable Long userId) {
        return ResponseEntity.ok(consentRepository.findByUserId(userId));
    }

    @PostMapping("/user/{userId}/grant")
    public ResponseEntity<Consent> grantConsent(
            @PathVariable Long userId,
            @RequestParam ConsentType type,
            @RequestParam(defaultValue = "v1.0") String version) {
        Consent consent = new Consent(userId, type, true, version);
        return ResponseEntity.ok(consentRepository.save(consent));
    }

    @PutMapping("/{consentId}/revoke")
    public ResponseEntity<?> revokeConsent(@PathVariable Long consentId) {
        return consentRepository.findById(consentId)
                .map(consent -> {
                    consent.setGranted(false);
                    consent.setRevokedAt(LocalDateTime.now());
                    return ResponseEntity.ok(consentRepository.save(consent));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}