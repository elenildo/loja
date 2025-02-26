package com.elenildo.loja.config;

import com.elenildo.loja.dto.ProductDto;
import com.elenildo.loja.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


public class ProductItemProcessor implements ItemProcessor<Product,Product> {
    private static final Logger log = LoggerFactory.getLogger(ProductItemProcessor.class);

    @Override
    public Product process(Product item) throws Exception {
        log.info("Executing any process...");
        return item;
    }
}
