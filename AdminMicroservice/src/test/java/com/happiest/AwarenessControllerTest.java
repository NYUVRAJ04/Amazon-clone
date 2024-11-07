package com.happiest;

import com.happiest.controller.AwarenessController;
import com.happiest.exception.FileStorageException;
import com.happiest.model.AwarenessContent;
import com.happiest.service.AwarenessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AwarenessControllerTest {

    @InjectMocks
    private AwarenessController awarenessController;

    @Mock
    private AwarenessService awarenessService;

    @Mock
    private MessageSource messageSource;

    @Mock
    private MultipartFile file;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadFileSuccess() {
        AwarenessContent content = new AwarenessContent();
        when(messageSource.getMessage("file.upload.success", new Object[]{file.getOriginalFilename()}, Locale.ENGLISH)).thenReturn("File uploaded successfully");

        ResponseEntity<String> response = awarenessController.uploadFile(file, "title", "desc", "contentType", true, Locale.ENGLISH);

        assertEquals("File uploaded successfully", response.getBody());
    }

    @Test
    void testUploadFileFailure() {
        when(awarenessService.saveAwarenessContent(any(AwarenessContent.class), eq(file))).thenThrow(new FileStorageException("Storage error"));
        when(messageSource.getMessage("file.upload.failure", new Object[]{"Storage error"}, Locale.ENGLISH)).thenReturn("File upload failed: Storage error");

        ResponseEntity<String> response = awarenessController.uploadFile(file, "title", "desc", "contentType", true, Locale.ENGLISH);

        assertEquals("File upload failed: Storage error", response.getBody());
    }

    @Test
    void testGetContent() {
        AwarenessContent content = new AwarenessContent();
        when(awarenessService.getContentById(1L)).thenReturn(content);

        ResponseEntity<AwarenessContent> response = awarenessController.getContent(1L, Locale.ENGLISH);

        assertEquals(ResponseEntity.ok(content), response);
    }
}
