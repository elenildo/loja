package com.elenildo.loja.model;

import com.elenildo.loja.dto.CategoryDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "categories")
@NoArgsConstructor
public class Category extends ModelEntity{

    @Column(unique = true, nullable = false)
    private String name;

    public Category(CategoryDto categoryDto) {
        name = categoryDto.getName();
    }
}
