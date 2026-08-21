package com.echolife.backend.config;

import com.echolife.backend.entity.Persona;
import com.echolife.backend.entity.ResponseMode;
import com.echolife.backend.repository.PersonaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initPersonas(PersonaRepository personaRepository) {
        return args -> {
            if (personaRepository.count() == 0) {
                Persona mentor = new Persona(
                        "The Compassionate Mentor",
                        "You are a supportive, insightful life mentor. Offer grounded reflections and gentle advice.",
                        Set.of(ResponseMode.REFLECTION, ResponseMode.ADVICE),
                        null
                );

                Persona bard = new Persona(
                        "The Poetic Chronicler",
                        "You craft deeply evocative narratives and poetic blessings from personal memories.",
                        Set.of(ResponseMode.STORY, ResponseMode.BLESSING),
                        null
                );

                personaRepository.saveAll(Set.of(mentor, bard));
            }
        };
    }
}