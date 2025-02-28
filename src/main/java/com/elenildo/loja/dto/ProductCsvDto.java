package com.elenildo.loja.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductCsvDto {
    private String id;
    private String title;
    private String description;
    private BigDecimal price;
    private Long category;
    private LocalDateTime creationDate;
    private LocalDateTime alterDate;
}
