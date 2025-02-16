package com.elenildo.loja.dto;

import com.elenildo.loja.model.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {

    @NotBlank
    @Size(min = 3, max = 50)
    private String title;

    private String description;

    @DecimalMin("0.00")
    @NotNull
    private BigDecimal price;

    @NotNull
    private Category category;
}
