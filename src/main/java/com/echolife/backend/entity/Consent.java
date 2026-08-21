package com.echolife.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consents")
public class Consent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConsentType consentType;

    @Column(nullable = false)
    private boolean granted;

    @Column(nullable = false)
    private LocalDateTime grantedAt;

    private LocalDateTime revokedAt;

    private String version;

    public Consent() {}

    public Consent(Long userId, ConsentType consentType, boolean granted, String version) {
        this.userId = userId;
        this.consentType = consentType;
        this.granted = granted;
        this.version = version;
        this.grantedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public ConsentType getConsentType() { return consentType; }
    public void setConsentType(ConsentType consentType) { this.consentType = consentType; }
    public boolean isGranted() { return granted; }
    public void setGranted(boolean granted) { this.granted = granted; }
    public LocalDateTime getGrantedAt() { return grantedAt; }
    public void setGrantedAt(LocalDateTime grantedAt) { this.grantedAt = grantedAt; }
    public LocalDateTime getRevokedAt() { return revokedAt; }
    public void setRevokedAt(LocalDateTime revokedAt) { this.revokedAt = revokedAt; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
}