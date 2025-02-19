package com.elenildo.loja.model;

import com.elenildo.loja.dto.ProductDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        title = productDto.getTitle().trim();
        description = productDto.getDescription();
        price = productDto.getPrice();
        category = productDto.getCategory();
//        images = productDto.getImages()
//                .stream().map(MultipartFile::getOriginalFilename)
//                .collect(Collectors.toSet());
    }
}
