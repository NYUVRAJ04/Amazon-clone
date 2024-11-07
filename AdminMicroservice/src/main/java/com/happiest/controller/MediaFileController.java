package com.happiest.controller;

import com.happiest.exception.MyFileNotFoundException;
import com.happiest.model.AwarenessContent;
import com.happiest.model.MediaFile;
import com.happiest.service.AwarenessService;
import com.happiest.service.MediaFileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/media-files")
@CrossOrigin
public class MediaFileController {

    private static final Logger logger = LogManager.getLogger(MediaFileController.class);


    @Autowired
    private MediaFileService mediaFileService;

    @Autowired
    private AwarenessService awarenessContentService;

    @GetMapping
    public List<MediaFile> getAll() {
        return mediaFileService.findAll();
    }

    @PostMapping
    public MediaFile create(@RequestBody MediaFile file) {
        return mediaFileService.save(file);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        mediaFileService.deleteById(id);
    }

    @GetMapping("/by-title")
    public List<MediaFile> getByAwarenessContentTitle(@RequestParam String title) {
        return mediaFileService.findByAwarenessContentTitle(title);
    }

    @PostMapping("/upload")
    public ResponseEntity<MediaFile> uploadMediaFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("awarenessContentId") Long awarenessContentId,
            @RequestParam("isDownloadable") boolean isDownloadable) {

        try {
            AwarenessContent awarenessContent = awarenessContentService.findById(awarenessContentId)
                    .orElseThrow(() -> new MyFileNotFoundException("AwarenessContent not found for ID " + awarenessContentId));

            MediaFile mediaFile = new MediaFile();
            mediaFile.setTitle(title);
            mediaFile.setFileType(file.getContentType());
            mediaFile.setFileUrl("/path/to/save/" + file.getOriginalFilename());
            mediaFile.setFileSize(file.getSize());
            mediaFile.setDownloadable(isDownloadable);
            mediaFile.setAwarenessContent(awarenessContent);

            MediaFile savedFile = mediaFileService.saveMediaFile(mediaFile, file);
            return ResponseEntity.ok(savedFile);
        } catch (IOException ex) {
            logger.error("Error saving media file: {}", ex.getMessage(), ex);
            return ResponseEntity.status(500).body(null);
        } catch (MyFileNotFoundException ex) {
            logger.error("AwarenessContent not found for ID {}: {}", awarenessContentId, ex.getMessage(), ex);
            return ResponseEntity.status(404).body(null);
        }
    }


    @GetMapping("/{id}")
    public MediaFile getById(@PathVariable Long id) {
        return mediaFileService.findById(id)
                .orElseThrow(() -> new RuntimeException("MediaFile not found"));
    }

    @GetMapping("/awareness-content/{awarenessContentId}")
    public ResponseEntity<List<MediaFile>> getMediaFilesByAwarenessContentId(@PathVariable Long awarenessContentId) {
        List<MediaFile> mediaFiles = mediaFileService.findByAwarenessContentId(awarenessContentId);
        return ResponseEntity.ok(mediaFiles);
    }
}
