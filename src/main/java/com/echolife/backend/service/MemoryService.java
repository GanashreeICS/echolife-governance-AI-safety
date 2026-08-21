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
import java.util.Optional;

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

    public Memory createMemory(Long userId, Memory memory) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return null;
        }

        // Validate Governance, Consent & Content Safety
        ResponseMode mode = memory.getResponseMode() != null ? memory.getResponseMode() : ResponseMode.REFLECTION;
        governanceService.validateAiExecution(userId, memory.getPersonaId(), mode, memory.getDescription());

        memory.setUser(userOpt.get());
        if (memory.getMemoryDate() == null) {
            memory.setMemoryDate(LocalDate.now());
        }

        aiReflectionService.generateReflection(memory);
        return memoryRepository.save(memory);
    }

    public Memory createMemoryFromPrompt(Long userId, Long promptId, Memory memory) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Prompt> promptOpt = promptRepository.findById(promptId);

        if (userOpt.isEmpty() || promptOpt.isEmpty()) {
            return null;
        }

        // Validate Governance, Consent & Content Safety
        ResponseMode mode = memory.getResponseMode() != null ? memory.getResponseMode() : ResponseMode.REFLECTION;
        governanceService.validateAiExecution(userId, memory.getPersonaId(), mode, memory.getDescription());

        memory.setUser(userOpt.get());
        memory.setPrompt(promptOpt.get());
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