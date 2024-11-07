package org.happiest.repository;

import org.happiest.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    // Additional custom queries for the Admin entity can be added here if needed


}
