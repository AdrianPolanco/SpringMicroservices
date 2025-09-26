package com.microservices.product_service.service;

import com.microservices.product_service.dto.ProductRequest;
import com.microservices.product_service.dto.ProductResponse;
import com.microservices.product_service.model.Product;
import com.microservices.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Marks this class as a Spring service component, hence a bean,making it eligible for component scanning and dependency injection
@RequiredArgsConstructor
@Slf4j // Lombok annotation to enable logging with log
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .price(productRequest.price())
                .description(productRequest.description())
                .build();

        product = productRepository.save(product);
        log.info("Product {} saved successfully", product.getId());

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepository.findAll();
        log.info("Fetched {} products", products.size());
        //ALTERNATIVE: products.stream().map(this::mapToProductResponse).toList();
        return products.stream().map(product -> new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        )).toList();
    }
}
