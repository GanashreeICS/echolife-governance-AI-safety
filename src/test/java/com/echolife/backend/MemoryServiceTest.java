package com.echolife.backend;

import com.echolife.backend.entity.Memory;
import com.echolife.backend.entity.Prompt;
import com.echolife.backend.entity.User;
import com.echolife.backend.repository.MemoryRepository;
import com.echolife.backend.repository.PromptRepository;
import com.echolife.backend.repository.UserRepository;
import com.echolife.backend.service.AiReflectionService;
import com.echolife.backend.service.MemoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemoryServiceTest {

    @Mock
    private MemoryRepository memoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PromptRepository promptRepository;

    @Mock
    private AiReflectionService aiReflectionService;

    @InjectMocks
    private MemoryService memoryService;

    private User sampleUser;
    private Prompt samplePrompt;
    private Memory sampleMemory;

    @BeforeEach
    void setUp() {
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setName("Sarah Connor");
        sampleUser.setEmail("sarah@example.com");

        samplePrompt = new Prompt();
        samplePrompt.setId(1L);
        samplePrompt.setQuestion("What is your happiest memory?");
        samplePrompt.setCategory("FAMILY");

        sampleMemory = new Memory();
        sampleMemory.setId(10L);
        sampleMemory.setTitle("Trip to the Alps");
        sampleMemory.setDescription("Quiet mornings and mountain views.");
        sampleMemory.setEmotionalTone("Peaceful");
        sampleMemory.setMemoryDate(LocalDate.now());
        sampleMemory.setIsTimeCapsule(false);
    }

    @Test
    @DisplayName("Should successfully create a memory and trigger AI reflection")
    void createMemory_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(memoryRepository.save(any(Memory.class))).thenReturn(sampleMemory);

        Memory created = memoryService.createMemory(1L, sampleMemory);

        assertNotNull(created);
        assertEquals("Trip to the Alps", created.getTitle());
        assertEquals(sampleUser, created.getUser());
        verify(aiReflectionService, times(1)).generateReflection(sampleMemory);
        verify(memoryRepository, times(1)).save(sampleMemory);
    }

    @Test
    @DisplayName("Should return null when creating memory for non-existent user")
    void createMemory_UserNotFound_ReturnsNull() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Memory result = memoryService.createMemory(99L, sampleMemory);

        assertNull(result);
        verify(aiReflectionService, never()).generateReflection(any());
        verify(memoryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should successfully create a memory linked to a prompt")
    void createMemoryFromPrompt_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(promptRepository.findById(1L)).thenReturn(Optional.of(samplePrompt));
        when(memoryRepository.save(any(Memory.class))).thenReturn(sampleMemory);

        Memory created = memoryService.createMemoryFromPrompt(1L, 1L, sampleMemory);

        assertNotNull(created);
        assertEquals(sampleUser, created.getUser());
        assertEquals(samplePrompt, created.getPrompt());
        verify(aiReflectionService, times(1)).generateReflection(sampleMemory);
        verify(memoryRepository, times(1)).save(sampleMemory);
    }

    @Test
    @DisplayName("Should return null when creating memory from prompt with invalid prompt ID")
    void createMemoryFromPrompt_InvalidPrompt_ReturnsNull() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(promptRepository.findById(99L)).thenReturn(Optional.empty());

        Memory result = memoryService.createMemoryFromPrompt(1L, 99L, sampleMemory);

        assertNull(result);
        verify(aiReflectionService, never()).generateReflection(any());
        verify(memoryRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should return accessible memories for user")
    void getAccessibleMemories_Success() {
        when(memoryRepository.findAccessibleMemories(eq(1L), any(LocalDate.class)))
                .thenReturn(List.of(sampleMemory));

        List<Memory> memories = memoryService.getAccessibleMemories(1L);

        assertNotNull(memories);
        assertEquals(1, memories.size());
        assertEquals("Trip to the Alps", memories.get(0).getTitle());
    }

    @Test
    @DisplayName("Should return locked time capsules for user")
    void getLockedTimeCapsules_Success() {
        sampleMemory.setIsTimeCapsule(true);
        sampleMemory.setUnlockDate(LocalDate.now().plusYears(4));

        when(memoryRepository.findLockedTimeCapsules(eq(1L), any(LocalDate.class)))
                .thenReturn(List.of(sampleMemory));

        List<Memory> capsules = memoryService.getLockedTimeCapsules(1L);

        assertNotNull(capsules);
        assertEquals(1, capsules.size());
        assertTrue(Boolean.TRUE.equals(capsules.get(0).getIsTimeCapsule()));
    }

    @Test
    @DisplayName("Should delete memory when ID exists")
    void deleteMemory_Exists_ReturnsTrue() {
        when(memoryRepository.existsById(10L)).thenReturn(true);
        doNothing().when(memoryRepository).deleteById(10L);

        boolean deleted = memoryService.deleteMemory(10L);

        assertTrue(deleted);
        verify(memoryRepository, times(1)).deleteById(10L);
    }

    @Test
    @DisplayName("Should return false when attempting to delete non-existent memory")
    void deleteMemory_NotExists_ReturnsFalse() {
        when(memoryRepository.existsById(999L)).thenReturn(false);

        boolean deleted = memoryService.deleteMemory(999L);

        assertFalse(deleted);
        verify(memoryRepository, never()).deleteById(anyLong());
    }
}