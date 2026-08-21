package com.echolife.backend.dto;

import com.echolife.backend.entity.ResponseMode;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class MemoryRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private String emotionalTone;
    private LocalDate memoryDate;
    private boolean isTimeCapsule;
    private LocalDate unlockDate;

    // Governance fields
    private Long personaId;
    private ResponseMode responseMode = ResponseMode.REFLECTION;

    public MemoryRequest() {}

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEmotionalTone() { return emotionalTone; }
    public void setEmotionalTone(String emotionalTone) { this.emotionalTone = emotionalTone; }

    public LocalDate getMemoryDate() { return memoryDate; }
    public void setMemoryDate(LocalDate memoryDate) { this.memoryDate = memoryDate; }

    public boolean isTimeCapsule() { return isTimeCapsule; }
    public void setTimeCapsule(boolean timeCapsule) { isTimeCapsule = timeCapsule; }

    public LocalDate getUnlockDate() { return unlockDate; }
    public void setUnlockDate(LocalDate unlockDate) { this.unlockDate = unlockDate; }

    public Long getPersonaId() { return personaId; }
    public void setPersonaId(Long personaId) { this.personaId = personaId; }

    public ResponseMode getResponseMode() { return responseMode; }
    public void setResponseMode(ResponseMode responseMode) { this.responseMode = responseMode; }
}