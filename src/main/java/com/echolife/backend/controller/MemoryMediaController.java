package com.echolife.backend.controller;

import com.echolife.backend.entity.Memory;
import com.echolife.backend.entity.MemoryMedia;
import com.echolife.backend.repository.MemoryRepository;
import com.echolife.backend.repository.MemoryMediaRepository;
import com.echolife.backend.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/media")
public class MemoryMediaController {

    private final MemoryMediaRepository mediaRepository;
    private final MemoryRepository memoryRepository;
    private final FileStorageService fileStorageService;

    public MemoryMediaController(MemoryMediaRepository mediaRepository,
                                 MemoryRepository memoryRepository,
                                 FileStorageService fileStorageService) {
        this.mediaRepository = mediaRepository;
        this.memoryRepository = memoryRepository;
        this.fileStorageService = fileStorageService;
    }

    // 1. Upload a real file attached to a Memory
    @PostMapping(value = "/memory/{memoryId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadMedia(@PathVariable Long memoryId,
                                         @RequestParam("file") MultipartFile file,
                                         @RequestParam("mediaType") String mediaType) {
        Memory memory = memoryRepository.findById(memoryId).orElse(null);
        if (memory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Memory not found with ID: " + memoryId);
        }

        String fileUrl = fileStorageService.storeFile(file);

        MemoryMedia media = new MemoryMedia();
        media.setMediaType(mediaType);
        media.setMediaUrl(fileUrl);
        media.setMemory(memory);

        MemoryMedia savedMedia = mediaRepository.save(media);
        return new ResponseEntity<>(savedMedia, HttpStatus.CREATED);
    }

    // 2. View/Display the uploaded image directly in the browser
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("uploads").toAbsolutePath().normalize().resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            // Determine dynamic content type (image/png, image/jpeg, etc.)
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 3. Get all media for a specific memory
    @GetMapping("/memory/{memoryId}")
    public ResponseEntity<List<MemoryMedia>> getMediaByMemoryId(@PathVariable Long memoryId) {
        return ResponseEntity.ok(mediaRepository.findByMemoryId(memoryId));
    }
}