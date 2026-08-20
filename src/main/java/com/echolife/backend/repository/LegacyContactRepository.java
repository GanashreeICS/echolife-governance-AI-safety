package com.echolife.backend.repository;

import com.echolife.backend.entity.LegacyContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LegacyContactRepository extends JpaRepository<LegacyContact, Long> {
    // Find all legacy contacts belonging to a specific user
    List<LegacyContact> findByUserId(Long userId);
}