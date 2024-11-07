package org.happiest.repository;

import org.happiest.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long>
{
    Optional<Seller> findByUserEmail(String email);

}
