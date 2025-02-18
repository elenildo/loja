package com.elenildo.loja.service;

import com.elenildo.loja.datatables.Datatables;
import com.elenildo.loja.datatables.DatatablesColunas;
import com.elenildo.loja.dto.CategoryDto;
import com.elenildo.loja.model.Category;
import com.elenildo.loja.repository.CategoryRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Datatables datatables;

    public boolean categoryExists(String name) {
        return categoryRepository.existsByNameIgnoreCase(name);
    }

    public void create(CategoryDto categoryDto) {
        categoryRepository.save(new Category(categoryDto));
    }

    public Map<String, Object> getCategories(HttpServletRequest request) {
        datatables.setRequest(request);
        datatables.setColunas(DatatablesColunas.CATEGORIES);
        Page<?> page = datatables.getSearch().isEmpty()?
                categoryRepository.findAll(datatables.getPageable()):
                categoryRepository.findAllByNameContaining(datatables.getSearch(), datatables.getPageable());
        return datatables.getResponse(page);
    }

    public List<Category> getAll() {
        return categoryRepository.findAllByOrderByName();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }
}
