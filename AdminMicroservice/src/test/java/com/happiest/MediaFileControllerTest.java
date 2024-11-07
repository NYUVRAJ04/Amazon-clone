package com.happiest;

import com.happiest.controller.MediaFileController;
import com.happiest.exception.MyFileNotFoundException;
import com.happiest.model.AwarenessContent;
import com.happiest.model.MediaFile;
import com.happiest.service.AwarenessService;
import com.happiest.service.MediaFileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MediaFileControllerTest {

    @InjectMocks
    private MediaFileController mediaFileController;

    @Mock
    private MediaFileService mediaFileService;

    @Mock
    private AwarenessService awarenessService;

    @Mock
    private MultipartFile file;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        List<MediaFile> mediaFiles = List.of(new MediaFile(), new MediaFile());
        when(mediaFileService.findAll()).thenReturn(mediaFiles);

        List<MediaFile> result = mediaFileController.getAll();

        assertEquals(mediaFiles, result);
    }

    @Test
    void testUploadMediaFileSuccess() throws IOException {
        MediaFile mediaFile = new MediaFile();
        when(awarenessService.findById(1L)).thenReturn(java.util.Optional.of(new AwarenessContent()));
        when(mediaFileService.saveMediaFile(any(MediaFile.class), eq(file))).thenReturn(mediaFile);

        ResponseEntity<MediaFile> response = mediaFileController.uploadMediaFile(file, "title", 1L, true);

        assertEquals(ResponseEntity.ok(mediaFile), response);
    }

    @Test
    void testUploadMediaFileNotFound() {
        when(awarenessService.findById(1L)).thenThrow(new MyFileNotFoundException("Content not found"));

        ResponseEntity<MediaFile> response = mediaFileController.uploadMediaFile(file, "title", 1L, true);

        assertEquals(404, response.getStatusCodeValue());
    }
}
