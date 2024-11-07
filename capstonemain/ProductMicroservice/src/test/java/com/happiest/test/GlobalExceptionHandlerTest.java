package com.happiest.test;

import com.happiest.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GlobalExceptionHandler.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        // Additional setup if necessary
    }

//    @Test
//    void testHandleOrderNotFoundException() throws Exception {
//        // Simulate an endpoint that throws OrderNotFoundException
//        mockMvc.perform(get("/someEndpointThatThrowsOrderNotFoundException"))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string(containsString("Order not found")));
//    }
//
//    @Test
//    void testHandleOrderProcessingException() throws Exception {
//        // Simulate an endpoint that throws OrderProcessingException
//        mockMvc.perform(get("/someEndpointThatThrowsOrderProcessingException"))
//                .andExpect(status().isInternalServerError())
//                .andExpect(content().string(containsString("Order processing error")));
//    }
//
//    @Test
//    void testHandleFileStorageException() throws Exception {
//        // Simulate an endpoint that throws FileStorageException
//        mockMvc.perform(get("/someEndpointThatThrowsFileStorageException"))
//                .andExpect(status().isInternalServerError())
//                .andExpect(content().string(containsString("File storage error")));
//    }
//
//    @Test
//    void testHandleMyFileNotFoundException() throws Exception {
//        // Simulate an endpoint that throws MyFileNotFoundException
//        mockMvc.perform(get("/someEndpointThatThrowsMyFileNotFoundException"))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string(containsString("File not found")));
//    }

    @Test
    void testHandleGenericException() throws Exception {
        // Simulate an endpoint that throws a generic exception
        mockMvc.perform(get("/someEndpointThatThrowsGenericException"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("An unexpected error occurred. Please try again.")));
    }
}
