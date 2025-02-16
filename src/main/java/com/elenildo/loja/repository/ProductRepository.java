package com.elenildo.loja.repository;

import com.elenildo.loja.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByTitleIgnoreCase(String title);

    Page<?> findAllByTitleContaining(String search, Pageable pageable);
}
