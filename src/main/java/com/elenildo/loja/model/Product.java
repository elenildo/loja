package com.elenildo.loja.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product extends ModelEntity{

    @Column(unique = true, nullable = false)
    private String title;

    @Lob
    private String description;

    private BigDecimal price;

    @ManyToOne
    private Category category;
}
