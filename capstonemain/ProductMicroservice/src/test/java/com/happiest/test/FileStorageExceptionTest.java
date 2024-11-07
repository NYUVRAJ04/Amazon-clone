package com.happiest.test;

import com.happiest.exception.FileStorageException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class FileStorageExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "File storage error";
        FileStorageException exception = new FileStorageException(message);

        // Assert that the message is set correctly
        assertEquals(message, exception.getMessage());
        // Assert that the cause is null
        assertNull(exception.getCause());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        String message = "File storage error with cause";
        Throwable cause = new Throwable("Root cause");
        FileStorageException exception = new FileStorageException(message, cause);

        // Assert that the message is set correctly
        assertEquals(message, exception.getMessage());
        // Assert that the cause is set correctly
        assertSame(cause, exception.getCause());
    }
}
