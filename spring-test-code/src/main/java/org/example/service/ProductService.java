package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.Product;
import org.example.domain.constant.ProductStatus;
import org.example.dto.request.CreateProductRequest;
import org.example.dto.response.ProductResponse;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductService {

    private static final String INITIAL_PRODUCT_NUMBER = "001";

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllByProductStatusIn(
                ProductStatus.sellingStatus());

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public ProductResponse createProduct(CreateProductRequest request) {
        String nextProductNumber = createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();

        if (latestProductNumber == null) {
            return INITIAL_PRODUCT_NUMBER;
        }

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        return String.format("%03d", latestProductNumberInt + 1);
    }
}
