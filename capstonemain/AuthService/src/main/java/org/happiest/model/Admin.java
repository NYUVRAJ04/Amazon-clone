package org.happiest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "admins")
public class Admin {

    @Id
    private String adminId; // Unique admin ID in the format "ADMXXXXX"

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "email", nullable = false)
    private Users user; // Foreign key reference to User table

    // Additional fields specific to Admin can be added here

    // Method to generate a unique admin ID
    public static String generateAdminId(int number) {
        return "ADM" + String.format("%05d", number); // Formats number with leading zeros to 5 digits
    }
}
