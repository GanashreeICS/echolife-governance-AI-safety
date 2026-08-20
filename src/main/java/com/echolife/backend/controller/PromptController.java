package com.echolife.backend.controller;

import com.echolife.backend.entity.Prompt;
import com.echolife.backend.service.PromptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prompts")
public class PromptController {

    private final PromptService promptService;

    public PromptController(PromptService promptService) {
        this.promptService = promptService;
    }

    // POST /api/prompts -> Create prompt
    @PostMapping
    public ResponseEntity<Prompt> createPrompt(@RequestBody Prompt prompt) {
        Prompt created = promptService.createPrompt(prompt);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET /api/prompts -> Get all prompts
    @GetMapping
    public ResponseEntity<List<Prompt>> getAllPrompts() {
        return ResponseEntity.ok(promptService.getAllPrompts());
    }

    // GET /api/prompts/active -> Get only active prompts
    @GetMapping("/active")
    public ResponseEntity<List<Prompt>> getActivePrompts() {
        return ResponseEntity.ok(promptService.getActivePrompts());
    }

    // GET /api/prompts/category/{category} -> Get prompts by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Prompt>> getPromptsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(promptService.getPromptsByCategory(category));
    }

    // DELETE /api/prompts/{id} -> Delete prompt
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrompt(@PathVariable Long id) {
        boolean isDeleted = promptService.deletePrompt(id);
        if (isDeleted) {
            return ResponseEntity.ok("Prompt with id " + id + " deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Cannot delete. Prompt not found with id: " + id);
    }
}