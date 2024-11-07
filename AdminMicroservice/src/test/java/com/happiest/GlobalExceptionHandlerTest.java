package com.happiest;

import com.happiest.exception.FileStorageException;
import com.happiest.exception.GlobalExceptionHandler;
import com.happiest.exception.MyFileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private MessageSource messageSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleFileStorageException() {
        FileStorageException exception = new FileStorageException("Storage error");
        when(messageSource.getMessage("error.file.storage", new Object[]{"Storage error"}, Locale.ENGLISH)).thenReturn("File storage error: Storage error");

        String result = globalExceptionHandler.handleFileStorageException(exception, Locale.ENGLISH);

        assertEquals("File storage error: Storage error", result);
    }

    @Test
    void testHandleMyFileNotFoundException() {
        MyFileNotFoundException exception = new MyFileNotFoundException("File not found");
        when(messageSource.getMessage("file.not.found", new Object[]{"File not found"}, Locale.ENGLISH)).thenReturn("File not found: File not found");

        String result = globalExceptionHandler.handleMyFileNotFoundException(exception, Locale.ENGLISH);

        assertEquals("File not found: File not found", result);
    }
}
