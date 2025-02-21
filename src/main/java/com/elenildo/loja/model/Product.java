package com.elenildo.loja.model;

import com.elenildo.loja.dto.ProductDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product extends ModelEntity{

    @Column(unique = true, nullable = false)
    private String title;

    @Column(columnDefinition="TEXT")
    private String description;

    private BigDecimal price;

    @ManyToOne
    private Category category;

    private Set<String> images;

    public Product(ProductDto productDto) {
        id = productDto.getId();
        title = productDto.getTitle().trim();
        description = productDto.getDescription();
        price = productDto.getPrice();
        category = productDto.getCategory();
    }
}
