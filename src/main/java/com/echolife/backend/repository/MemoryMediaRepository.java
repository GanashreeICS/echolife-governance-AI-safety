package com.echolife.backend.repository;

import com.echolife.backend.entity.MemoryMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoryMediaRepository extends JpaRepository<MemoryMedia, Long> {
    // Find all media files attached to a specific memory ID
    List<MemoryMedia> findByMemoryId(Long memoryId);
}