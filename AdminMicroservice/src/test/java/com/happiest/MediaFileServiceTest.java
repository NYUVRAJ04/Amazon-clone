//package com.happiest;
//
//import com.happiest.exception.MyFileNotFoundException;
//import com.happiest.service.FileStorageService;
//import com.happiest.service.MediaFileService;
//import com.happiest.utility.UploadFileResponse;
//import com.happiest.model.MediaFile;
//import com.happiest.repository.MediaFileRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class MediaFileServiceTest {
//
//    @Mock
//    private MediaFileRepository mediaFileRepository;
//
//    @Mock
//    private FileStorageService fileStorageService;
//
//    @Mock
//    private MultipartFile file;
//
//    @InjectMocks
//    private MediaFileService mediaFileService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testSaveMediaFileSuccess() throws Exception {
//        // Mock MediaFile and UploadFileResponse objects
//        MediaFile mediaFile = mock(MediaFile.class);
//        UploadFileResponse uploadFileResponse = new UploadFileResponse("testfile.txt", "fileUri", "fileType", 100L);
//
//        // Mock file storage service to return a valid upload response
//        when(fileStorageService.storeFile(file)).thenReturn(uploadFileResponse);
//
//        // Mock repository save operation to return the mediaFile object
//        when(mediaFileRepository.save(any(MediaFile.class))).thenReturn(mediaFile);
//
//        MediaFile result = mediaFileService.saveMediaFile(mediaFile, file);
//
//        assertNotNull(result);
//        verify(mediaFileRepository, times(1)).save(mediaFile);
//        verify(fileStorageService, times(1)).storeFile(file);
//    }
//
//    @Test
//    void testSaveMediaFileIOException() {
//        // Mock MediaFile object
//        MediaFile mediaFile = mock(MediaFile.class);
//
//        // Simulate an exception when storing the file
//        doThrow(new RuntimeException("File storage failed")).when(fileStorageService).storeFile(file);
//
//        assertThrows(RuntimeException.class, () -> mediaFileService.saveMediaFile(mediaFile, file));
//        verify(fileStorageService, times(1)).storeFile(file);
//    }
//
//    @Test
//    void testFindById_NotFound() {
//        // Mock repository to return an empty Optional
//        when(mediaFileRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(RuntimeException.class, () -> mediaFileService.findById(1L));
//    }
//
//    @Test
//    void testFindById_Found() {
//        // Mock a MediaFile object and repository to return it
//        MediaFile mediaFile = mock(MediaFile.class);
//        when(mediaFileRepository.findById(1L)).thenReturn(Optional.of(mediaFile));
//
//        MediaFile result = mediaFileService.findById(1L);
//
//        assertNotNull(result);
//        assertEquals(mediaFile, result);
//        verify(mediaFileRepository, times(1)).findById(1L);
//    }
//}



package com.happiest;

import com.happiest.model.MediaFile;
import com.happiest.repository.MediaFileRepository;
import com.happiest.service.FileStorageService;
import com.happiest.service.MediaFileService;
import com.happiest.utility.UploadFileResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MediaFileServiceTest {

    @Mock
    private MediaFileRepository mediaFileRepository;

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private MediaFileService mediaFileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<MediaFile> mediaFiles = List.of(new MediaFile(), new MediaFile());
        when(mediaFileRepository.findAll()).thenReturn(mediaFiles);

        List<MediaFile> result = mediaFileService.findAll();

        assertEquals(2, result.size());
        verify(mediaFileRepository, times(1)).findAll();
    }

    @Test
    void testSave() {
        MediaFile mediaFile = new MediaFile();
        when(mediaFileRepository.save(mediaFile)).thenReturn(mediaFile);

        MediaFile result = mediaFileService.save(mediaFile);

        assertNotNull(result);
        verify(mediaFileRepository, times(1)).save(mediaFile);
    }

    @Test
    void testDeleteById() {
        Long id = 1L;
        doNothing().when(mediaFileRepository).deleteById(id);

        mediaFileService.deleteById(id);

        verify(mediaFileRepository, times(1)).deleteById(id);
    }

    @Test
    void testFindByAwarenessContentTitle() {
        String title = "Awareness Content Title";
        List<MediaFile> mediaFiles = List.of(new MediaFile(), new MediaFile());
        when(mediaFileRepository.findByAwarenessContent_Title(title)).thenReturn(mediaFiles);

        List<MediaFile> result = mediaFileService.findByAwarenessContentTitle(title);

        assertEquals(2, result.size());
        verify(mediaFileRepository, times(1)).findByAwarenessContent_Title(title);
    }

    @Test
    void testFindById_Found() {
        MediaFile mediaFile = new MediaFile();
        when(mediaFileRepository.findById(1L)).thenReturn(Optional.of(mediaFile));

        Optional<MediaFile> result = mediaFileService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(mediaFile, result.get());
        verify(mediaFileRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(mediaFileRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<MediaFile> result = mediaFileService.findById(1L);

        assertFalse(result.isPresent());
        verify(mediaFileRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByAwarenessContentId() {
        Long awarenessContentId = 1L;
        List<MediaFile> mediaFiles = List.of(new MediaFile(), new MediaFile());
        when(mediaFileRepository.findByAwarenessContentId(awarenessContentId)).thenReturn(mediaFiles);

        List<MediaFile> result = mediaFileService.findByAwarenessContentId(awarenessContentId);

        assertEquals(2, result.size());
        verify(mediaFileRepository, times(1)).findByAwarenessContentId(awarenessContentId);
    }

    @Test
    void testSaveMediaFileWithFile() throws IOException {
        // Create a mock MediaFile and UploadFileResponse
        MediaFile mediaFile = new MediaFile();
        UploadFileResponse uploadFileResponse = new UploadFileResponse("testfile.txt", "fileUri", "fileType", 100L);

        // Mock the multipart file and file storage service
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getContentType()).thenReturn("image/png");
        when(multipartFile.getSize()).thenReturn(1024L);
        when(fileStorageService.storeFile(multipartFile)).thenReturn(uploadFileResponse);

        // Mock repository save operation
        when(mediaFileRepository.save(any(MediaFile.class))).thenReturn(mediaFile);

        MediaFile result = mediaFileService.saveMediaFile(mediaFile, multipartFile);

        assertNotNull(result);
        assertEquals("fileUri", mediaFile.getFileUrl());
        assertEquals("image/png", mediaFile.getFileType());
        assertEquals(1024L, mediaFile.getFileSize());
        assertNotNull(mediaFile.getCreatedAt());

        verify(fileStorageService, times(1)).storeFile(multipartFile);
        verify(mediaFileRepository, times(1)).save(mediaFile);
    }

    @Test
    void testSaveMediaFileWithoutFile() throws IOException {
        // Create a mock MediaFile without file upload
        MediaFile mediaFile = new MediaFile();
        when(mediaFileRepository.save(any(MediaFile.class))).thenReturn(mediaFile);

        MediaFile result = mediaFileService.saveMediaFile(mediaFile, null);

        assertNotNull(result);
        assertNull(mediaFile.getFileUrl());
        assertNull(mediaFile.getFileType());
        assertNull(mediaFile.getFileSize());
        assertNotNull(mediaFile.getCreatedAt());

        verify(fileStorageService, times(0)).storeFile(any(MultipartFile.class));
        verify(mediaFileRepository, times(1)).save(mediaFile);
    }

    @Test
    void testSaveMediaFileThrowsIOException() {
        MediaFile mediaFile = new MediaFile();

        // Mock file to be non-empty
        when(multipartFile.isEmpty()).thenReturn(false);

        // Simulate a RuntimeException in file storage to replace the IOException
        when(fileStorageService.storeFile(multipartFile)).thenThrow(new RuntimeException("File storage error"));

        assertThrows(RuntimeException.class, () -> mediaFileService.saveMediaFile(mediaFile, multipartFile));

        verify(fileStorageService, times(1)).storeFile(multipartFile);
        verify(mediaFileRepository, times(0)).save(mediaFile);
    }

}
