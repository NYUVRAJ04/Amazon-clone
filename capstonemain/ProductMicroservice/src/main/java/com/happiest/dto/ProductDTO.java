package com.happiest.dto;

import com.happiest.model.Machinery;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ProductDTO {
    private String category;
    private String availability;
    private String categoryName;
    private String description;
    private String imageUrl;
    private String name;
    private Double rentalPrice;
    private Double sellingPrice;
}