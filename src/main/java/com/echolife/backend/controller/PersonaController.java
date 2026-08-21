package com.echolife.backend.controller;

import com.echolife.backend.entity.Persona;
import com.echolife.backend.repository.PersonaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    private final PersonaRepository personaRepository;

    public PersonaController(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Persona>> getAvailablePersonas(@PathVariable Long userId) {
        return ResponseEntity.ok(personaRepository.findByUserIdOrUserIdIsNull(userId));
    }

    @PostMapping
    public ResponseEntity<Persona> createCustomPersona(@RequestBody Persona persona) {
        return ResponseEntity.ok(personaRepository.save(persona));
    }
}
