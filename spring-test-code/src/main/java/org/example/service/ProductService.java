package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.Product;
import org.example.domain.constant.ProductStatus;
import org.example.dto.response.ProductResponse;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllByProductStatusIn(
                ProductStatus.sellingStatus());

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }
}
