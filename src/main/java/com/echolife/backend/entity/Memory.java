package com.echolife.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name = "memories")
public class Memory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate memoryDate;

    // Time Capsule Attributes
    private Boolean isTimeCapsule = false;
    private LocalDate unlockDate;

    // AI & Prompt Linkage Attributes
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prompt_id")
    private Prompt prompt;

    @Column(columnDefinition = "TEXT")
    private String aiReflectionSummary;

    private String emotionalTone; // e.g. Nostalgic, Grateful, Joyful

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Memory() {
    }

    public Memory(String title, String description, LocalDate memoryDate, Boolean isTimeCapsule, LocalDate unlockDate, User user) {
        this.title = title;
        this.description = description;
        this.memoryDate = memoryDate;
        this.isTimeCapsule = isTimeCapsule != null ? isTimeCapsule : false;
        this.unlockDate = unlockDate;
        this.user = user;
    }

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

    public Prompt getPrompt() {
        return prompt;
    }

    public void setPrompt(Prompt prompt) {
        this.prompt = prompt;
    }

    public String getAiReflectionSummary() {
        return aiReflectionSummary;
    }

    public void setAiReflectionSummary(String aiReflectionSummary) {
        this.aiReflectionSummary = aiReflectionSummary;
    }

    public String getEmotionalTone() {
        return emotionalTone;
    }

    public void setEmotionalTone(String emotionalTone) {
        this.emotionalTone = emotionalTone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}