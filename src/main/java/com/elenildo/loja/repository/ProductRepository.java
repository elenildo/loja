package com.elenildo.loja.repository;

import com.elenildo.loja.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByTitleIgnoreCase(String title);

    @Query("select p from Product p join fetch p.category where p.title ilike %:search%")
    Page<?> findAllByTitleContaining(String search, Pageable pageable);

    Optional<Product> findByTitleIgnoreCase(String title);

    @Query("select p from Product p join fetch p.category")
    List<Product> getAll();

    @Query("select p from Product p join fetch p.category")
    Page<Product> findAllPageable(Pageable pageable);
}
