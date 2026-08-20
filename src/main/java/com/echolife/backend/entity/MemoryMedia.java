package com.echolife.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "memory_media")
public class MemoryMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileUrl;

    @Column(nullable = false)
    private String mediaType; // e.g., IMAGE, AUDIO, VIDEO

    // Relationship: Many media items belong to one Memory
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memory_id", nullable = false)
    @JsonIgnore // Prevents infinite JSON loop when returning data
    private Memory memory;

    public MemoryMedia() {
    }

    public MemoryMedia(String fileUrl, String mediaType, Memory memory) {
        this.fileUrl = fileUrl;
        this.mediaType = mediaType;
        this.memory = memory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public void setMediaUrl(String fileUrl) {
    }
}