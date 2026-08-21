package com.echolife.backend.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "personas")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String systemPrompt;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "persona_allowed_modes", joinColumns = @JoinColumn(name = "persona_id"))
    @Column(name = "mode")
    private Set<ResponseMode> allowedModes = new HashSet<>();

    private boolean active = true;

    private Long userId;

    public Persona() {}

    public Persona(String name, String systemPrompt, Set<ResponseMode> allowedModes, Long userId) {
        this.name = name;
        this.systemPrompt = systemPrompt;
        this.allowedModes = allowedModes;
        this.userId = userId;
        this.active = true;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSystemPrompt() { return systemPrompt; }
    public void setSystemPrompt(String systemPrompt) { this.systemPrompt = systemPrompt; }
    public Set<ResponseMode> getAllowedModes() { return allowedModes; }
    public void setAllowedModes(Set<ResponseMode> allowedModes) { this.allowedModes = allowedModes; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}