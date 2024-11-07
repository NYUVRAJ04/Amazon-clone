package com.happiest.repository;

import com.happiest.model.Machinery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import  java.util.List;

public interface MachineryRepository extends JpaRepository<Machinery,Long>
{
    List<Machinery> findByNameContainingOrCategoryNameContaining(String name, String categoryName);

    @Query("SELECT DISTINCT m.categoryName FROM Machinery m")
    List<String> findDistinctCategoryNames();

    List<Machinery> findByCategoryName(String categoryName);
}
