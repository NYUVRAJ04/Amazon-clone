package com.happiest.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "admins")
public class Admin {

    @Id
    private String adminId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "email", nullable = false)
    private Users user;
}
