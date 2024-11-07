package com.happiest.repository;

import com.happiest.model.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaFileRepository extends JpaRepository<MediaFile, Long> {
    List<MediaFile> findByAwarenessContent_Title(String title);

    List<MediaFile> findByAwarenessContentId(Long awarenessContentId);
}
