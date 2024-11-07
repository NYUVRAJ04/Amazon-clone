package com.happiest;

import com.happiest.exception.FileStorageException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

 public class FileStorageExceptionTest {

    @Test
    void testFileStorageException_withMessageOnly() {
        FileStorageException exception = new FileStorageException("File storage error occurred");

        assertEquals("File storage error occurred", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testFileStorageException_withMessageAndCause() {
        Throwable cause = new RuntimeException("Underlying cause");
        FileStorageException exception = new FileStorageException("File storage error occurred", cause);

        assertEquals("File storage error occurred", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
