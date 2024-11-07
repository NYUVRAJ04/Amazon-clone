package com.happiest.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class Users {

    @Id
    private String email;  // Email as the unique user identifier
    private String name;
    private String role;
}
