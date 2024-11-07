package org.happiest.controller;

import org.happiest.apigateway.AwarenessInterface;
import org.happiest.constants.LoggerConstants;
import org.happiest.model.AwarenessContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/awareness")
public class AwarenessController {

    private static final Logger logger = LoggerFactory.getLogger(AwarenessController.class);

    @Autowired
    private AwarenessInterface awarenessInterface;

    @GetMapping("/allcontent")
    public ResponseEntity<List<AwarenessContent>> getAllContent() {
        logger.info(LoggerConstants.LOG_FETCHING_ALL_AWARENESS_CONTENT);
        ResponseEntity<List<AwarenessContent>> response = awarenessInterface.getAllContent();
        logger.info(LoggerConstants.LOG_RETURNING_AWARENESS_CONTENT_COUNT,
                response.getBody() != null ? response.getBody().size() : 0);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AwarenessContent> getContentById(@PathVariable Long id) {
        logger.info(LoggerConstants.LOG_FETCHING_AWARENESS_CONTENT_BY_ID, id);
        ResponseEntity<AwarenessContent> response = awarenessInterface.getContentById(id);
        if (response.getBody() != null) {
            logger.info(LoggerConstants.LOG_CONTENT_FOUND, response.getBody());
        } else {
            logger.warn(LoggerConstants.LOG_NO_CONTENT_FOUND, id);
        }
        return response;
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<AwarenessContent>> getContentByCategory(@PathVariable Long categoryId) {
        logger.info(LoggerConstants.LOG_FETCHING_AWARENESS_CONTENT_BY_CATEGORY, categoryId);
        ResponseEntity<List<AwarenessContent>> response = awarenessInterface.getContentByCategory(categoryId);
        logger.info(LoggerConstants.LOG_RETURNING_AWARENESS_CONTENT_FOR_CATEGORY_COUNT,
                response.getBody() != null ? response.getBody().size() : 0, categoryId);
        return response;
    }

    @PostMapping
    public ResponseEntity<AwarenessContent> createContent(@RequestBody AwarenessContent content) {
        logger.info(LoggerConstants.LOG_CREATING_NEW_AWARENESS_CONTENT, content);
        ResponseEntity<AwarenessContent> response = awarenessInterface.createContent(content);
        logger.info(LoggerConstants.LOG_CREATED_AWARENESS_CONTENT_ID, response.getBody().getId());
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<AwarenessContent> updateContent(@PathVariable Long id, @RequestBody AwarenessContent updatedContent) {
        logger.info(LoggerConstants.LOG_UPDATING_AWARENESS_CONTENT, id);
        ResponseEntity<AwarenessContent> response = awarenessInterface.updateContent(id, updatedContent);
        logger.info(LoggerConstants.LOG_UPDATED_AWARENESS_CONTENT_ID, response.getBody().getId());
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        logger.info(LoggerConstants.LOG_DELETING_AWARENESS_CONTENT, id);
        ResponseEntity<Void> response = awarenessInterface.deleteContent(id);
        logger.info(LoggerConstants.LOG_DELETED_AWARENESS_CONTENT_ID, id);
        return response;
    }
}
