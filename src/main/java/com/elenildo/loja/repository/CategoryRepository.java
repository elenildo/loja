package com.elenildo.loja.repository;

import com.elenildo.loja.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByOrderByName();
    boolean existsByNameIgnoreCase(String name);
    Page<?> findAllByNameContaining(String search, Pageable pageable);
    Optional<Category> findByNameIgnoreCase(String name);
}
