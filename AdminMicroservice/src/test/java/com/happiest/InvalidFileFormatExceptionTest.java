package com.happiest;

import com.happiest.exception.InvalidFileFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class InvalidFileFormatExceptionTest {

    @Test
    void testInvalidFileFormatException_withMessageOnly() {
        InvalidFileFormatException exception = new InvalidFileFormatException("Invalid file format");

        assertEquals("Invalid file format", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testInvalidFileFormatException_withMessageAndCause() {
        Throwable cause = new IllegalArgumentException("Unsupported file type");
        InvalidFileFormatException exception = new InvalidFileFormatException("Invalid file format", cause);

        assertEquals("Invalid file format", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
