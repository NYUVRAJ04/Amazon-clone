package com.happiest.controller;

import com.happiest.exception.FileStorageException;
import com.happiest.exception.MyFileNotFoundException;
import com.happiest.model.AwarenessContent;
import com.happiest.service.AwarenessService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/awareness")
@CrossOrigin
public class AwarenessController {

    @Autowired
    private AwarenessService service;

//    @Autowired
//    public AwarenessController(AwarenessService service) {
//        this.service = service;
//    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("contentType") String contentType,
            @RequestParam("isDownloadable") boolean isDownloadable,
            Locale locale) {

        AwarenessContent content = new AwarenessContent();
        content.setTitle(title);
        content.setDescription(description);
        content.setContentType(contentType);
        content.setDownloadable(isDownloadable);

        try {
            service.saveAwarenessContent(content, file);
            String message = messageSource.getMessage("file.upload.success", new Object[]{file.getOriginalFilename()}, locale);
            return ResponseEntity.ok(message);
        } catch (FileStorageException ex) {
            String errorMessage = messageSource.getMessage("file.upload.failure", new Object[]{ex.getMessage()}, locale);
            return ResponseEntity.status(500).body(errorMessage);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<AwarenessContent> getContent(@PathVariable Long id, Locale locale) {
        try {
            AwarenessContent content = service.getContentById(id);
            return ResponseEntity.ok(content);
        } catch (MyFileNotFoundException ex) {
            String errorMessage = messageSource.getMessage("error.content.not.found", new Object[]{id}, locale);
            logger.error(errorMessage, ex);
            return ResponseEntity.status(404).body(null);
        }
    }

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LogManager.getLogger(AwarenessController.class);


    @GetMapping("/all")
    public ResponseEntity<List<AwarenessContent>> getAllContent() {
        List<AwarenessContent> contentList = service.getAllContent();
        return ResponseEntity.ok(contentList);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        try {
            AwarenessContent content = service.getContentById(id);
            Resource resource = service.loadFileAsResource(content.getFileUrl());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(content.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + content.getFileUrl() + "\"")
                    .body(resource);
        } catch (MyFileNotFoundException ex) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/api/content/{contentType}")
    public List<AwarenessContent> getContentByType(@PathVariable String contentType) {
        return service.getContentByType(contentType);
    }
}
