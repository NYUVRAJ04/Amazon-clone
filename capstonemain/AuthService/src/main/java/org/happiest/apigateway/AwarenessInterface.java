package org.happiest.apigateway;

import org.happiest.model.AwarenessContent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "http://AdminMicroservice/educate")
public interface AwarenessInterface
{
    @GetMapping("/allcontent")
    public ResponseEntity<List<AwarenessContent>> getAllContent();

    @GetMapping("/{id}")
    public ResponseEntity<AwarenessContent> getContentById(@PathVariable Long id);

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<AwarenessContent>> getContentByCategory(@PathVariable Long categoryId);

    @PostMapping
    public ResponseEntity<AwarenessContent> createContent(@RequestBody AwarenessContent content);

    @PutMapping("/{id}")
    public ResponseEntity<AwarenessContent> updateContent(@PathVariable Long id, @RequestBody AwarenessContent updatedContent);

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id);

    @PostMapping(value = "/upload",consumes = "multipart/form-data")
    public ResponseEntity<AwarenessContent> uploadFile(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam("contentType") String contentType);



}
