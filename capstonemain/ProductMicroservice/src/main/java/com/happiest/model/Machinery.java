package com.happiest.model;

import java.math.BigDecimal;

import com.happiest.dto.ProductDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "machinery")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Machinery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Column(name = "rental_price")
    private Double rentalPrice;

    @Column(name = "selling_price")
    private Double sellingPrice;

    @Enumerated(EnumType.STRING)
    private Availability machineryavailability;  // Enum for availability

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "image_url")
    private String imageUrl;  // Field for storing image URL




    // Enum for availability status
    public enum Availability {
        Available,
        Rented,
        AVAILABLE, Sold
    }
}
