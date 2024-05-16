package org.example.service;

import org.example.dto.request.CreateProductRequest;
import org.example.integration.ServiceTestContext;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.domain.constant.ProductStatus.*;
import static org.example.domain.constant.ProductType.*;
import static org.example.utils.ProductFixture.createProduct;

class ProductServiceTest extends ServiceTestContext {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void end() {
        productRepository.deleteAllInBatch();
    }

    @DisplayName("신규 상품을 등록 시 상품 번호는 가장 최근 상품 번호에서 1 증가한 값으로 설정된다.")
    @Test
    void createProductTest() {
        var product1 = createProduct("001", BAKERY, SELLING, "아메리카노", 4000);
        var product2 = createProduct("002", BOTTLE, HOLD, "카페라떼", 4500);
        var product3 = createProduct("003", HANDMADE, STOP, "팥빙수", 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        var request = CreateProductRequest.builder()
                .productType(HANDMADE)
                .productStatus(SELLING)
                .name("아이스크림")
                .price(5000)
                .build();

        var response = productService.createProduct(request);

        assertThat(response)
                .extracting("productNumber", "name", "price")
                .contains("004", "아이스크림", 5000);

        var products = productRepository.findAll();

        assertThat(products).hasSize(4);
    }

    @DisplayName("신규 상품을 등록 시 상품이 비어 있을 경우 새로 등록된 상품의 상품 번호는 001로 저장된다.")
    @Test
    void createProductWhenProductIsEmpty() {
        var request = CreateProductRequest.builder()
                .productType(HANDMADE)
                .productStatus(SELLING)
                .name("아이스크림")
                .price(5000)
                .build();

        var response = productService.createProduct(request);

        assertThat(response)
                .extracting("productNumber", "name", "price")
                .contains("001", "아이스크림", 5000);

        var products = productRepository.findAll();

        assertThat(products).hasSize(1);
    }
}
