
package com.happiest.EligibilityMicroservice.repository;

import com.happiest.EligibilityMicroservice.model.BuyersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyersRepository extends JpaRepository<BuyersEntity, Long> {
}
