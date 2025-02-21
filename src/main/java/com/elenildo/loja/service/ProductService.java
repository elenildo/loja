package com.elenildo.loja.service;

import com.elenildo.loja.datatables.Datatables;
import com.elenildo.loja.datatables.DatatablesColunas;
import com.elenildo.loja.dto.ProductDto;
import com.elenildo.loja.model.Category;
import com.elenildo.loja.model.Product;
import com.elenildo.loja.repository.ProductRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final Datatables datatables;

    @Value("${upload.images}")
    private String homeDirectory;

    @Value("${upload.csv}")
    private String csvDirectory;

    public ProductService(ProductRepository productRepository, Datatables datatables) {
        this.productRepository = productRepository;
        this.datatables = datatables;
    }

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
        String uploadDirectory = homeDirectory+"products/";
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
            paths.add("/upload/images/products/" + file.getOriginalFilename());
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

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void remove(Long id) {
        removeFiles(id);
        productRepository.deleteById(id);
    }

    private void removeFiles(Long id) {
        var product = findById(id);
        if(product.isPresent()){
            if(product.get().getImages() != null)
                product.get().getImages()
                        .forEach(path -> removeFile("src/main/resources/static/"+path));
        }
    }

    private void removeFile(String fullPath) {
        File fileToDelete = new File(fullPath);
        fileToDelete.delete();
    }

    public Optional<Product> findByTitleIgnoreCase(String title) {
        return productRepository.findByTitleIgnoreCase(title);
    }

    public void saveCsvFile(MultipartFile file) throws IOException, CsvException {
        String uploadDirectory = csvDirectory+"products/";
        File directory = new File(uploadDirectory);
        Path path;
        String fullPath;

        if (!directory.exists())
            directory.mkdirs();

        byte[] bytes = file.getBytes();
        fullPath = uploadDirectory + file.getOriginalFilename();
        path = Paths.get(fullPath);
        Files.write(path, bytes);

        saveCsvFileToDatabase(fullPath);
        removeFile(fullPath);
    }

    private List<String[]> readCsvFile(String fullPath) throws IOException, CsvException {
        CSVReader reader = new CSVReader(new FileReader(fullPath));
        return reader.readAll();
    }

    private void saveCsvFileToDatabase(String fullPath) throws IOException, CsvException {
        var rows = readCsvFile(fullPath);
        List<Product> products = new ArrayList<>();

        for(String[] row : rows) {
            if(row.length < 5) continue;
            var product  = new Product();
            product.setId(row[0].isEmpty() ? null : Long.valueOf(row[0].replaceAll("\\uFEFF", "")));
            product.setTitle(row[1].isEmpty() ? null : row[1]);
            product.setDescription(row[2]);
            product.setPrice(row[3].isEmpty() ? new BigDecimal(0) : new BigDecimal(row[3]));
            product.setCategory(row[4].isEmpty() ? null : new Category(Long.parseLong(row[4])));
            products.add(product);
        }
        productRepository.saveAll(products);

    }
}
