package org.happiest.test;

import org.happiest.apigateway.AwarenessInterface;
import org.happiest.constants.LoggerConstants;
import org.happiest.controller.AwarenessController;
import org.happiest.model.AwarenessContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AwarenessControllerTest {

    @InjectMocks
    private AwarenessController awarenessController;

    @Mock
    private AwarenessInterface awarenessInterface;

    private static final Logger logger = LoggerFactory.getLogger(AwarenessControllerTest.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllContent() {
        AwarenessContent content1 = new AwarenessContent(); // Initialize with necessary fields
        AwarenessContent content2 = new AwarenessContent(); // Initialize with necessary fields
        List<AwarenessContent> contentList = Arrays.asList(content1, content2);
        
        when(awarenessInterface.getAllContent()).thenReturn(ResponseEntity.ok(contentList));

        ResponseEntity<List<AwarenessContent>> response = awarenessController.getAllContent();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(contentList.size(), response.getBody().size());
    }

    @Test
    void testGetContentById_Found() {
        Long contentId = 1L;
        AwarenessContent content = new AwarenessContent(); // Initialize with necessary fields
        content.setId(contentId);

        when(awarenessInterface.getContentById(contentId)).thenReturn(ResponseEntity.ok(content));

        ResponseEntity<AwarenessContent> response = awarenessController.getContentById(contentId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(content, response.getBody());
    }

    @Test
    void testGetContentById_NotFound() {
        Long contentId = 1L;

        when(awarenessInterface.getContentById(contentId)).thenReturn(ResponseEntity.notFound().build());

        ResponseEntity<AwarenessContent> response = awarenessController.getContentById(contentId);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals(null, response.getBody());
    }

    @Test
    void testGetContentByCategory() {
        Long categoryId = 1L;
        AwarenessContent content1 = new AwarenessContent(); // Initialize with necessary fields
        AwarenessContent content2 = new AwarenessContent(); // Initialize with necessary fields
        List<AwarenessContent> contentList = Arrays.asList(content1, content2);
        
        when(awarenessInterface.getContentByCategory(categoryId)).thenReturn(ResponseEntity.ok(contentList));

        ResponseEntity<List<AwarenessContent>> response = awarenessController.getContentByCategory(categoryId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(contentList.size(), response.getBody().size());
    }

    @Test
    void testCreateContent() {
        AwarenessContent newContent = new AwarenessContent(); // Initialize with necessary fields
        newContent.setId(1L); // Assume an ID is set after creation

        when(awarenessInterface.createContent(newContent)).thenReturn(ResponseEntity.ok(newContent));

        ResponseEntity<AwarenessContent> response = awarenessController.createContent(newContent);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(newContent.getId(), response.getBody().getId());
    }

    @Test
    void testUpdateContent() {
        Long contentId = 1L;
        AwarenessContent updatedContent = new AwarenessContent(); // Initialize with necessary fields
        updatedContent.setId(contentId); // Set the ID to be updated

        when(awarenessInterface.updateContent(contentId, updatedContent)).thenReturn(ResponseEntity.ok(updatedContent));

        ResponseEntity<AwarenessContent> response = awarenessController.updateContent(contentId, updatedContent);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedContent.getId(), response.getBody().getId());
    }

    @Test
    void testDeleteContent() {
        Long contentId = 1L;

        when(awarenessInterface.deleteContent(contentId)).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<Void> response = awarenessController.deleteContent(contentId);

        assertEquals(204, response.getStatusCodeValue());
    }
}
