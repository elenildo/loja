package com.elenildo.loja.dto;

import com.elenildo.loja.model.Category;
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

    @NotBlank
    @Size(min = 3, max = 50)
    private String title;

    private String description;

    @DecimalMin("0.00")
    @NotNull
    private BigDecimal price;

    @NotNull
    private Category category;

    private Set<MultipartFile> images;
}
