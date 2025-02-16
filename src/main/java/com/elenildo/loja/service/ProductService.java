package com.elenildo.loja.service;

import com.elenildo.loja.datatables.Datatables;
import com.elenildo.loja.datatables.DatatablesColunas;
import com.elenildo.loja.dto.ProductDto;
import com.elenildo.loja.model.Product;
import com.elenildo.loja.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final Datatables datatables;

    public boolean productExists(String title) {
        return productRepository.existsByTitleIgnoreCase(title);
    }

    public void create(ProductDto productDto) {
        productRepository.save(new Product(productDto));
    }

    public Map<String, Object> getProducts(HttpServletRequest request) {
        datatables.setRequest(request);
        datatables.setColunas(DatatablesColunas.PRODUCTS);
        Page<?> page = datatables.getSearch().isEmpty()?
                productRepository.findAll(datatables.getPageable()):
                productRepository.findAllByTitleContaining(datatables.getSearch(), datatables.getPageable());
        return datatables.getResponse(page);

    }
}
