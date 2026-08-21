package com.echolife.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "memories")
public class Memory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Description is required")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private String emotionalTone;

    private LocalDate memoryDate;

    private Boolean isTimeCapsule = false;

    private LocalDate unlockDate;

    @Column(columnDefinition = "TEXT")
    private String aiReflection;

    @Column(columnDefinition = "TEXT")
    private String aiReflectionSummary;

    // Governance & Persona Extensions
    private Long personaId;

    @Enumerated(EnumType.STRING)
    private ResponseMode responseMode = ResponseMode.REFLECTION;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"password", "memories"})
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prompt_id")
    private Prompt prompt;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Memory() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.memoryDate == null) {
            this.memoryDate = LocalDate.now();
        }
        if (this.isTimeCapsule == null) {
            this.isTimeCapsule = false;
        }
        if (this.responseMode == null) {
            this.responseMode = ResponseMode.REFLECTION;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmotionalTone() {
        return emotionalTone;
    }

    public void setEmotionalTone(String emotionalTone) {
        this.emotionalTone = emotionalTone;
    }

    public LocalDate getMemoryDate() {
        return memoryDate;
    }

    public void setMemoryDate(LocalDate memoryDate) {
        this.memoryDate = memoryDate;
    }

    public Boolean getIsTimeCapsule() {
        return isTimeCapsule;
    }

    public void setIsTimeCapsule(Boolean timeCapsule) {
        isTimeCapsule = timeCapsule;
    }

    public LocalDate getUnlockDate() {
        return unlockDate;
    }

    public void setUnlockDate(LocalDate unlockDate) {
        this.unlockDate = unlockDate;
    }

    public String getAiReflection() {
        return aiReflection;
    }

    public void setAiReflection(String aiReflection) {
        this.aiReflection = aiReflection;
    }

    public String getAiReflectionSummary() {
        return aiReflectionSummary;
    }

    public void setAiReflectionSummary(String aiReflectionSummary) {
        this.aiReflectionSummary = aiReflectionSummary;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public ResponseMode getResponseMode() {
        return responseMode != null ? responseMode : ResponseMode.REFLECTION;
    }

    public void setResponseMode(ResponseMode responseMode) {
        this.responseMode = responseMode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Prompt getPrompt() {
        return prompt;
    }

    public void setPrompt(Prompt prompt) {
        this.prompt = prompt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}