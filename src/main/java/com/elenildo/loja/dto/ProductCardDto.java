package com.elenildo.loja.dto;

import com.elenildo.loja.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductCardDto {
    private Long id;
    private String title;
    private BigDecimal price;
    private String image;

    public ProductCardDto(Product product) {
        id = product.getId();
        title = product.getTitle();
        price = product.getPrice();
        image = product.getImages() == null || product.getImages().isEmpty() ?
                null :
                product.getImages().stream().findFirst().get();
    }
}
