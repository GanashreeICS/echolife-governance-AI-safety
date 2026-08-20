package com.echolife.backend.service;

import com.echolife.backend.entity.Memory;
import com.echolife.backend.entity.MemoryMedia;
import com.echolife.backend.repository.MemoryMediaRepository;
import com.echolife.backend.repository.MemoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoryMediaService {

    private final MemoryMediaRepository memoryMediaRepository;
    private final MemoryRepository memoryRepository;

    public MemoryMediaService(MemoryMediaRepository memoryMediaRepository, MemoryRepository memoryRepository) {
        this.memoryMediaRepository = memoryMediaRepository;
        this.memoryRepository = memoryRepository;
    }

    // 1. Add a media attachment to a specific Memory
    public MemoryMedia addMediaToMemory(Long memoryId, MemoryMedia media) {
        Memory memory = memoryRepository.findById(memoryId).orElse(null);
        if (memory == null) {
            return null; // Cannot add media if memory doesn't exist
        }
        media.setMemory(memory);
        return memoryMediaRepository.save(media);
    }

    // 2. Get all media files for a specific memory
    public List<MemoryMedia> getMediaByMemoryId(Long memoryId) {
        return memoryMediaRepository.findByMemoryId(memoryId);
    }

    // 3. Delete a media file by ID
    public boolean deleteMedia(Long id) {
        if (memoryMediaRepository.existsById(id)) {
            memoryMediaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}