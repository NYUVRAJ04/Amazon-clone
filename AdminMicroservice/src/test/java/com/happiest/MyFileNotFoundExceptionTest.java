package com.happiest;

import com.happiest.exception.MyFileNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

 public class MyFileNotFoundExceptionTest {

    @Test
    void testMyFileNotFoundException_withMessageOnly() {
        MyFileNotFoundException exception = new MyFileNotFoundException("File not found");

        assertEquals("File not found", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testMyFileNotFoundException_withMessageAndCause() {
        Throwable cause = new RuntimeException("Root cause");
        MyFileNotFoundException exception = new MyFileNotFoundException("File not found", cause);

        assertEquals("File not found", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
