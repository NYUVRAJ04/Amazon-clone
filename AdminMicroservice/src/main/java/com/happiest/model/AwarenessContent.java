package com.happiest.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AwarenessContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String contentType; // e.g., pdf, video, image
    private String fileType;    // MIME type, e.g., application/pdf, video/mp4, image/jpeg
    private String fileUrl;     // Path or URL for the file location
    private boolean isDownloadable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long fileSize;      // Size of the file in bytes

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
