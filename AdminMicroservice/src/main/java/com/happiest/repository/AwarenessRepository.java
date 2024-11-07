package com.happiest.repository;

import com.happiest.model.AwarenessContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AwarenessRepository extends JpaRepository<AwarenessContent, Long>
{
    List<AwarenessContent> findByContentType(String contentType);
}
