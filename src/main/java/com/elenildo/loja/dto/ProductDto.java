package com.elenildo.loja.dto;

import com.elenildo.loja.model.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {

    @NotBlank
    @Size(min = 3, max = 50)
    private String title;

    private String description;

    @NotBlank
    @DecimalMin("0")
    private BigDecimal price;

    private Category category;
}
