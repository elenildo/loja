package com.elenildo.loja.config;

import com.elenildo.loja.dto.ProductCsvDto;
import com.elenildo.loja.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.net.MalformedURLException;

@Configuration
@AllArgsConstructor
public class BatchConfig {

    @Bean
    public FlatFileItemReader<ProductCsvDto> reader() throws MalformedURLException {
        return new FlatFileItemReaderBuilder<ProductCsvDto>()
                .name("productItemReader")
                .resource(new ClassPathResource("/static/upload/csv/products/lanc.csv"))
                .strict(false)
                .delimited()
                .names("id", "title", "description", "price", "category")
                .targetType(ProductCsvDto.class)
                .build();
    }

    @Bean
    public ProductItemProcessor processor() {
        return new ProductItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<ProductCsvDto> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<ProductCsvDto>()
                .sql("INSERT INTO products (title, description, price, category_id) VALUES (:title, :description, :price, :category)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
        return new JobBuilder("importProductJob", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
                      FlatFileItemReader<ProductCsvDto> reader, ProductItemProcessor processor, JdbcBatchItemWriter<ProductCsvDto> writer) {
        return new StepBuilder("step1", jobRepository)
                .<ProductCsvDto, ProductCsvDto>chunk(5, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
