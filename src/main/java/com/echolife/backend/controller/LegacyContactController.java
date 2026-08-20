package com.echolife.backend.controller;

import com.echolife.backend.entity.LegacyContact;
import com.echolife.backend.service.LegacyContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class LegacyContactController {

    private final LegacyContactService contactService;

    public LegacyContactController(LegacyContactService contactService) {
        this.contactService = contactService;
    }

    // POST /api/contacts/user/{userId} -> Add a contact for a user
    @PostMapping("/user/{userId}")
    public ResponseEntity<?> addContact(@PathVariable Long userId, @RequestBody LegacyContact contact) {
        LegacyContact savedContact = contactService.addContact(userId, contact);
        if (savedContact == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cannot add contact. User not found with id: " + userId);
        }
        return new ResponseEntity<>(savedContact, HttpStatus.CREATED);
    }

    // GET /api/contacts/user/{userId} -> Get all contacts of a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LegacyContact>> getContactsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(contactService.getContactsByUserId(userId));
    }

    // DELETE /api/contacts/{id} -> Delete a contact
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id) {
        boolean isDeleted = contactService.deleteContact(id);
        if (isDeleted) {
            return ResponseEntity.ok("Contact with id " + id + " removed successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Cannot delete. Contact not found with id: " + id);
    }
}