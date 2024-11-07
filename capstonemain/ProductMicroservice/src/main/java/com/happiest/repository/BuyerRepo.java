package com.happiest.repository;

import com.happiest.model.Buyers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepo extends JpaRepository<Buyers,Long> {
}
