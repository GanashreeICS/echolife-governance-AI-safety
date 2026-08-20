package com.echolife.backend.controller;

import com.echolife.backend.entity.Memory;
import com.echolife.backend.service.MemoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memories")
public class MemoryController {

    private final MemoryService memoryService;

    public MemoryController(MemoryService memoryService) {
        this.memoryService = memoryService;
    }

    // Create memory for user
    @PostMapping("/user/{userId}")
    public ResponseEntity<?> createMemory(@PathVariable Long userId, @Valid @RequestBody Memory memory) {
        Memory createdMemory = memoryService.createMemory(userId, memory);
        if (createdMemory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cannot create memory. User not found with id: " + userId);
        }
        return new ResponseEntity<>(createdMemory, HttpStatus.CREATED);
    }

    // Create memory answering a Prompt
    @PostMapping("/user/{userId}/prompt/{promptId}")
    public ResponseEntity<?> createMemoryFromPrompt(@PathVariable Long userId,
                                                    @PathVariable Long promptId,
                                                    @Valid @RequestBody Memory memory) {
        Memory createdMemory = memoryService.createMemoryFromPrompt(userId, promptId, memory);
        if (createdMemory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cannot create memory. Invalid User ID (" + userId + ") or Prompt ID (" + promptId + ").");
        }
        return new ResponseEntity<>(createdMemory, HttpStatus.CREATED);
    }

    // Get all memories
    @GetMapping
    public ResponseEntity<List<Memory>> getAllMemories() {
        return ResponseEntity.ok(memoryService.getAllMemories());
    }

    // Get all memories of a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Memory>> getMemoriesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(memoryService.getMemoriesByUserId(userId));
    }

    // Get unlocked memories
    @GetMapping("/user/{userId}/unlocked")
    public ResponseEntity<List<Memory>> getAccessibleMemories(@PathVariable Long userId) {
        return ResponseEntity.ok(memoryService.getAccessibleMemories(userId));
    }

    // Get locked time capsules
    @GetMapping("/user/{userId}/time-capsules")
    public ResponseEntity<List<Memory>> getLockedTimeCapsules(@PathVariable Long userId) {
        return ResponseEntity.ok(memoryService.getLockedTimeCapsules(userId));
    }

    // Get single memory by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getMemoryById(@PathVariable Long id) {
        Memory memory = memoryService.getMemoryById(id);
        if (memory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Memory not found with id: " + id);
        }
        return ResponseEntity.ok(memory);
    }

    // Delete memory
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMemory(@PathVariable Long id) {
        boolean isDeleted = memoryService.deleteMemory(id);
        if (isDeleted) {
            return ResponseEntity.ok("Memory with id " + id + " deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Cannot delete. Memory not found with id: " + id);
    }
}