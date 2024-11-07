package com.happiest.repository;

import com.happiest.model.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CropRepository extends JpaRepository<Crop,Long>
{
    @Query("SELECT DISTINCT c.categoryName FROM Crop c")
    List<String> findDistinctCategoryNames();

    List<Crop> findByNameContainingOrCategoryNameContaining(String name, String categoryName);

    List<Crop> findByCategoryName(String categoryName);
}
