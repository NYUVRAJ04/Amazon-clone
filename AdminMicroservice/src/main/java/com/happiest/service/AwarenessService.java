package com.happiest.service;

import com.happiest.exception.FileStorageException;
import com.happiest.exception.MyFileNotFoundException;
import com.happiest.model.AwarenessContent;
import com.happiest.repository.AwarenessRepository;
import com.happiest.utility.UploadFileResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AwarenessService {

    private final Path fileStorageLocation;

    private final AwarenessRepository repository;
    private final FileStorageService fileStorageService;

    private static final Logger logger = LogManager.getLogger(AwarenessService.class);


    @Autowired
    public AwarenessService(AwarenessRepository repository, FileStorageService fileStorageService) {
        this.repository = repository;
        this.fileStorageService = fileStorageService;
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
        createUploadDirectory();
    }

    private void createUploadDirectory() {
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create upload directory!", ex);
        }
    }

    public AwarenessContent saveAwarenessContent(AwarenessContent content, MultipartFile file) {
        logger.info("Saving AwarenessContent with title: {}", content.getTitle());
        try {
            if (file != null && !file.isEmpty()) {
                UploadFileResponse uploadFileResponse = fileStorageService.storeFile(file);
                content.setFileUrl(uploadFileResponse.getFileDownloadUri());
                content.setFileType(file.getContentType());
                content.setFileSize(file.getSize());
            }
            content.setCreatedAt(LocalDateTime.now());
            logger.info("Successfully saved AwarenessContent with title: {}", content.getTitle());
            return repository.save(content);
        } catch (Exception ex) {
            logger.error("Error saving AwarenessContent with title: {}", content.getTitle(), ex);
            throw new FileStorageException("Failed to save AwarenessContent", ex);
        }
    }

    public AwarenessContent getContentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new MyFileNotFoundException("Content not found for ID " + id));
    }

    public List<AwarenessContent> getAllContent() {
        return repository.findAll();
    }

    public List<AwarenessContent> getContentByType(String contentType) {
        return repository.findByContentType(contentType);
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found: " + fileName, ex);
        }
    }

    public Optional<AwarenessContent> findById(Long id) {
        return repository.findById(id);
    }
}
