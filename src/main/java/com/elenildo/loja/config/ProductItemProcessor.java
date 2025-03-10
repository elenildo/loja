package com.elenildo.loja.config;

import com.elenildo.loja.dto.ProductCsvDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;


public class ProductItemProcessor implements ItemProcessor<ProductCsvDto,ProductCsvDto> {
    private static final Logger log = LoggerFactory.getLogger(ProductItemProcessor.class);

    @Override
    public ProductCsvDto process(ProductCsvDto productCsvDto) throws Exception {
//        log.info("Executing any process...");
        var prod = new ProductCsvDto();
        var _id = productCsvDto.getId().replaceAll("\\uFEFF", "");
        prod.setId( _id.isEmpty() ? null : _id);
        prod.setTitle(productCsvDto.getTitle().trim());
        prod.setDescription(productCsvDto.getDescription());
        prod.setPrice(productCsvDto.getPrice());
        prod.setCategory(productCsvDto.getCategory());
        prod.setCreationDate(LocalDateTime.now());
        prod.setAlterDate(LocalDateTime.now());
        Thread.sleep(10);
        return prod;
    }
}
