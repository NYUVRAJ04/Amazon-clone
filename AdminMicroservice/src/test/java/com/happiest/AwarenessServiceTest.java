package com.happiest;

import com.happiest.exception.FileStorageException;
import com.happiest.exception.MyFileNotFoundException;
import com.happiest.model.AwarenessContent;
import com.happiest.repository.AwarenessRepository;
import com.happiest.service.AwarenessService;
import com.happiest.service.FileStorageService;
import com.happiest.utility.UploadFileResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AwarenessServiceTest {

    @Mock
    private AwarenessRepository repository;

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private AwarenessService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveAwarenessContent() {
        AwarenessContent content = new AwarenessContent();
        content.setTitle("Test Title");

        when(file.isEmpty()).thenReturn(false);
        when(fileStorageService.storeFile(file)).thenReturn(new UploadFileResponse("filename", "fileUri", "fileType", 123L));
        when(repository.save(content)).thenReturn(content);

        service.saveAwarenessContent(content, file);

        verify(fileStorageService).storeFile(file);
        verify(repository).save(content);
    }

    @Test
    void testGetContentById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(MyFileNotFoundException.class, () -> service.getContentById(1L));
    }

    @Test
    void testLoadFileAsResource_NotFound() {
        assertThrows(MyFileNotFoundException.class, () -> service.loadFileAsResource("nonexistent.txt"));
    }

    @Test
    void testSaveAwarenessContent_withNullFile() {
        AwarenessContent content = new AwarenessContent();
        content.setTitle("No File");

        when(repository.save(content)).thenReturn(content);

        service.saveAwarenessContent(content, null);

        verify(repository).save(content);
    }

    @Test
    void testLoadFileAsResource_notFound() {
        assertThrows(MyFileNotFoundException.class, () -> service.loadFileAsResource("non_existent_file.txt"));
    }

    @Test
    void testGetAllContent() {
        List<AwarenessContent> contents = List.of(new AwarenessContent(), new AwarenessContent());
        when(repository.findAll()).thenReturn(contents);

        List<AwarenessContent> result = service.getAllContent();

        assertEquals(contents, result);
        verify(repository).findAll();
    }

    @Test
    void testGetContentByType() {
        List<AwarenessContent> contents = List.of(new AwarenessContent());
        when(repository.findByContentType("video")).thenReturn(contents);

        List<AwarenessContent> result = service.getContentByType("video");

        assertEquals(contents, result);
        verify(repository).findByContentType("video");
    }


}
