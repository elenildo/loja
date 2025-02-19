package com.elenildo.loja.service;

import com.elenildo.loja.datatables.Datatables;
import com.elenildo.loja.datatables.DatatablesColunas;
import com.elenildo.loja.dto.ProductDto;
import com.elenildo.loja.model.Product;
import com.elenildo.loja.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final Datatables datatables;

    public boolean productExists(String title) {
        return productRepository.existsByTitleIgnoreCase(title);
    }

    public void create(ProductDto productDto, MultipartFile[] files) throws IOException {
        var product = new Product(productDto);
        product.setImages(saveFiles(files));
        productRepository.save(product);
    }

    private Set<String> saveFiles(MultipartFile[] files) throws IOException {
        Set<String> paths = new HashSet<>();
        String uploadDirectory = "/home/apps/loja/images/products/";
        File directory = new File(uploadDirectory);
        Path path;
        String fullPath;

        if (!directory.exists())
            directory.mkdirs();

        for (MultipartFile file : files) {
            if(file.isEmpty()) continue;
            byte[] bytes = file.getBytes();
            fullPath = uploadDirectory + file.getOriginalFilename();
            path = Paths.get(fullPath);
            Files.write(path, bytes);
            paths.add(fullPath);
        }
        return paths;
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
