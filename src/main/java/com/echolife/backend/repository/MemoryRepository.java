package com.echolife.backend.repository;

import com.echolife.backend.entity.Memory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MemoryRepository extends JpaRepository<Memory, Long> {

    List<Memory> findByUserId(Long userId);

    // Find unlocked memories: either not a time capsule, OR unlock date has arrived/passed
    @Query("SELECT m FROM Memory m WHERE m.user.id = :userId AND (m.isTimeCapsule = false OR m.unlockDate <= :currentDate)")
    List<Memory> findAccessibleMemories(@Param("userId") Long userId, @Param("currentDate") LocalDate currentDate);

    // Find locked time capsules for a user
    @Query("SELECT m FROM Memory m WHERE m.user.id = :userId AND m.isTimeCapsule = true AND m.unlockDate > :currentDate")
    List<Memory> findLockedTimeCapsules(@Param("userId") Long userId, @Param("currentDate") LocalDate currentDate);
}