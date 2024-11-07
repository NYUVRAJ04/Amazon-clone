package com.happiest.EligibilityMicroservice.repository;

import com.happiest.EligibilityMicroservice.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
}
