package com.happiest;

import com.happiest.exception.FileStorageException;
import com.happiest.exception.MyFileNotFoundException;
import com.happiest.service.FileStorageService;
import com.happiest.utility.UploadFileResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

class FileStorageServiceTest {

    @Mock
    private MultipartFile file;

    @InjectMocks
    private FileStorageService fileStorageService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStoreFileSuccess() throws IOException {
        // Mock file attributes
        when(file.getOriginalFilename()).thenReturn("testfile.txt");
        when(file.getContentType()).thenReturn("text/plain");
        when(file.getSize()).thenReturn(100L);

        // Mock InputStream to prevent NullPointerException
        InputStream inputStream = new ByteArrayInputStream("test content".getBytes());
        when(file.getInputStream()).thenReturn(inputStream);

        UploadFileResponse response = fileStorageService.storeFile(file);

        assertNotNull(response);
        assertEquals("testfile.txt", response.getFileName());
        verify(file, times(1)).getInputStream();
    }


    @Test
    void testStoreFileWithInvalidPath() {
        when(file.getOriginalFilename()).thenReturn("..\\testfile.txt");
        assertThrows(FileStorageException.class, () -> fileStorageService.storeFile(file));
    }

    @Test
    void testLoadFileAsResourceSuccess() throws Exception {
        // Use a spy to control the behavior of loadFileAsResource
        FileStorageService fileStorageServiceSpy = spy(fileStorageService);
        String fileName = "testfile.txt";

        // Mock the behavior of loadFileAsResource to return a UrlResource
        Path path = Paths.get("uploads").resolve(fileName).normalize();
        Resource resource = new UrlResource(path.toUri());
        doReturn(resource).when(fileStorageServiceSpy).loadFileAsResource(fileName);

        Resource result = fileStorageServiceSpy.loadFileAsResource(fileName);
        assertNotNull(result);
    }

    @Test
    void testLoadFileAsResourceNotFound() {
        assertThrows(MyFileNotFoundException.class, () -> fileStorageService.loadFileAsResource("nonexistent.txt"));
    }
}
