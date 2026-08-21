package com.echolife.backend.service;

import com.echolife.backend.entity.Memory;
import com.echolife.backend.entity.Prompt;
import com.echolife.backend.entity.ResponseMode;
import com.echolife.backend.entity.User;
import com.echolife.backend.repository.MemoryRepository;
import com.echolife.backend.repository.PromptRepository;
import com.echolife.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemoryService {

    private final MemoryRepository memoryRepository;
    private final UserRepository userRepository;
    private final PromptRepository promptRepository;
    private final AiReflectionService aiReflectionService;
    private final GovernanceService governanceService;

    public MemoryService(MemoryRepository memoryRepository,
                         UserRepository userRepository,
                         PromptRepository promptRepository,
                         AiReflectionService aiReflectionService,
                         GovernanceService governanceService) {
        this.memoryRepository = memoryRepository;
        this.userRepository = userRepository;
        this.promptRepository = promptRepository;
        this.aiReflectionService = aiReflectionService;
        this.governanceService = governanceService;
    }

    // 1. Standard Memory Creation with Governance validation & AI Reflection
    public Memory createMemory(Long userId, Memory memory) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }

        // Validate Consent, Persona, Response Mode & Content Safety
        ResponseMode mode = memory.getResponseMode() != null ? memory.getResponseMode() : ResponseMode.REFLECTION;
        governanceService.validateAiExecution(userId, memory.getPersonaId(), mode, memory.getDescription());

        memory.setUser(user);
        if (memory.getMemoryDate() == null) {
            memory.setMemoryDate(LocalDate.now());
        }

        aiReflectionService.generateReflection(memory);
        return memoryRepository.save(memory);
    }

    // 2. Create Memory in response to a specific Prompt with Governance validation
    public Memory createMemoryFromPrompt(Long userId, Long promptId, Memory memory) {
        User user = userRepository.findById(userId).orElse(null);
        Prompt prompt = promptRepository.findById(promptId).orElse(null);

        if (user == null || prompt == null) {
            return null;
        }

        // Validate Consent, Persona, Response Mode & Content Safety
        ResponseMode mode = memory.getResponseMode() != null ? memory.getResponseMode() : ResponseMode.REFLECTION;
        governanceService.validateAiExecution(userId, memory.getPersonaId(), mode, memory.getDescription());

        memory.setUser(user);
        memory.setPrompt(prompt);
        if (memory.getMemoryDate() == null) {
            memory.setMemoryDate(LocalDate.now());
        }

        aiReflectionService.generateReflection(memory);
        return memoryRepository.save(memory);
    }

    public List<Memory> getAllMemories() {
        return memoryRepository.findAll();
    }

    public List<Memory> getMemoriesByUserId(Long userId) {
        return memoryRepository.findByUserId(userId);
    }

    public List<Memory> getAccessibleMemories(Long userId) {
        return memoryRepository.findAccessibleMemories(userId, LocalDate.now());
    }

    public List<Memory> getLockedTimeCapsules(Long userId) {
        return memoryRepository.findLockedTimeCapsules(userId, LocalDate.now());
    }

    public Memory getMemoryById(Long id) {
        return memoryRepository.findById(id).orElse(null);
    }

    public boolean deleteMemory(Long id) {
        if (memoryRepository.existsById(id)) {
            memoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}