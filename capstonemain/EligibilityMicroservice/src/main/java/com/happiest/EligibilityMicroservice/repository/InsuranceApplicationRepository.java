// src/main/java/com/happiest/EligibilityMicroservice/repository/InsuranceApplicationRepository.java

package com.happiest.EligibilityMicroservice.repository;

import com.happiest.EligibilityMicroservice.model.InsuranceApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceApplicationRepository extends JpaRepository<InsuranceApplication, Long> {
    List<InsuranceApplication> findByBuyerBuyerId(Long buyerId);
}
