package com.happiest;

import com.happiest.exception.ServiceCommunicationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ServiceCommunicationExceptionTest {

    @Test
    void testServiceCommunicationException_withMessageOnly() {
        // Passing null as the second argument for the cause
        ServiceCommunicationException exception = new ServiceCommunicationException("Service communication error", null);

        assertEquals("Service communication error", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testServiceCommunicationException_withMessageAndCause() {
        Throwable cause = new RuntimeException("Connection timeout");
        ServiceCommunicationException exception = new ServiceCommunicationException("Service communication error", cause);

        assertEquals("Service communication error", exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
