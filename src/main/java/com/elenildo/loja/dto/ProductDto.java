package com.elenildo.loja.dto;

import com.elenildo.loja.model.Category;
import com.elenildo.loja.model.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String title;

    private String description;

    @DecimalMin("0.00")
    @NotNull
    private BigDecimal price;

    @NotNull
    private Category category;

    private List<MultipartFile> images;

    private Set<String> imageSources;

    public ProductDto(Product product) {
        id = product.getId();
        title = product.getTitle().trim();
        description = product.getDescription();
        price = product.getPrice();
        category = product.getCategory();
        imageSources = product.getImages();
    }
}
