package org.happiest.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="resources")
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
    private String contentType; // e.g., video, article, infographic
    private String fileUrl; // URL for the content file

    private boolean isDownloadable;

    private Long categoryId; // Machinery or Crop Category Id from Eligibility Microservice

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors, getters, and setters
}
