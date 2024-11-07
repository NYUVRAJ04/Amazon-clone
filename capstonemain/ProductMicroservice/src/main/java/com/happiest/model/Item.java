package com.happiest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private String name;
    private String image;
    private double rentalPrice;
    private String description;
    private double sellingPrice;
    private int quantity;
    private int rentalDuration;
    private String uniqueId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
