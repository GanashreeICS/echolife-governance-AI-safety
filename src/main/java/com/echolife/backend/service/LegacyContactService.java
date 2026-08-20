package com.echolife.backend.service;

import com.echolife.backend.entity.LegacyContact;
import com.echolife.backend.entity.User;
import com.echolife.backend.repository.LegacyContactRepository;
import com.echolife.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LegacyContactService {

    private final LegacyContactRepository legacyContactRepository;
    private final UserRepository userRepository;

    public LegacyContactService(LegacyContactRepository legacyContactRepository, UserRepository userRepository) {
        this.legacyContactRepository = legacyContactRepository;
        this.userRepository = userRepository;
    }

    // 1. Add a legacy contact for a user
    public LegacyContact addContact(Long userId, LegacyContact contact) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        contact.setUser(user);
        return legacyContactRepository.save(contact);
    }

    // 2. Get all legacy contacts of a user
    public List<LegacyContact> getContactsByUserId(Long userId) {
        return legacyContactRepository.findByUserId(userId);
    }

    // 3. Delete a contact by ID
    public boolean deleteContact(Long id) {
        if (legacyContactRepository.existsById(id)) {
            legacyContactRepository.deleteById(id);
            return true;
        }
        return false;
    }
}