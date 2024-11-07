package org.happiest.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "crops")
@Data  // Generates getters, setters, toString, equals, and hashCode methods
public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "rental_price")
    private Double rentalPrice;

    @Column(name = "selling_price")
    private Double sellingPrice;

    @Enumerated(EnumType.STRING)
    private Availability cropavailability;  // Enum for availability

    @Column(name = "image_url")
    private String imageUrl;  // Field for storing image URL

    public enum Availability {
        Available,
        Sold
    }
}
