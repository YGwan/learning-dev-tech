package org.example.service;

import org.example.domain.Product;
import org.example.dto.request.CreateProductRequest;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.domain.constant.ProductStatus.*;
import static org.example.domain.constant.ProductType.HANDMADE;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private static final String LATEST_PRODUCT_NUMBER = "003";

    @BeforeEach
    public void init() {
        var product1 = Product.builder()
                .productNumber("001")
                .productType(HANDMADE)
                .productStatus(SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        var product2 = Product.builder()
                .productNumber("002")
                .productType(HANDMADE)
                .productStatus(HOLD)
                .name("카페라떼")
                .price(4500)
                .build();

        var product3 = Product.builder()
                .productNumber(LATEST_PRODUCT_NUMBER)
                .productType(HANDMADE)
                .productStatus(STOP)
                .name("팥빙수")
                .price(7000)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));
    }

    @AfterEach
    void end() {
        productRepository.deleteAllInBatch();
    }

    @DisplayName("신규 상품을 등록 시 상품 번호는 가장 최근 상품 번호에서 1 증가한 값으로 설정된다.")
    @Test
    void createProduct() {
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
        productRepository.deleteAllInBatch();

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
