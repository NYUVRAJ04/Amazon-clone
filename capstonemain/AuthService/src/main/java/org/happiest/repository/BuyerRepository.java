package org.happiest.repository;

import org.happiest.model.Buyers;
import  java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuyerRepository extends JpaRepository<Buyers, Long>
{
   Optional<Buyers> findByUserEmail(String email);


}
