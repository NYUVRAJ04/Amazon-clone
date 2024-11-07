package org.happiest.repository;

import org.happiest.model.Admin;
import org.happiest.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {

    Optional<Users> findByEmail(String email);

    @Modifying
    @Query("UPDATE Users u SET u.password = ?1 WHERE u.email = ?2")
    void updatePassword(String newPassword, String email);

    Optional<Users> findByRole(String role);
}
