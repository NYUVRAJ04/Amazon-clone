package org.happiest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String name;
    private String role;
    private Long buyerId;  // Nullable: present if role is BUYER
    private Long sellerId; // Nullable: present if role is SELLER
    private String message; // Optional: For error messages
}
