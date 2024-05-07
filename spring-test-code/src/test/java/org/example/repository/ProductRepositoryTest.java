package org.example.repository;

import org.example.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.example.domain.constant.ProductStatus.*;
import static org.example.domain.constant.ProductType.HANDMADE;

@DataJpaTest
class ProductRepositoryTest {

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

    @DisplayName("판매상태에 따른 상품들을 조회한다.")
    @Test
    void findAllByProductStatus() {
        var products = productRepository.findAllByProductStatusIn(List.of(SELLING, HOLD));

        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "productStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );
    }

    @DisplayName("상품 번호 리스트에 따른 상품들을 조회한다.")
    @Test
    void findAllByProductNumbers() {
        var products = productRepository.findAllByProductNumberIn(List.of("001", "002"));

        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "productStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );
    }

    @DisplayName("가장 마지막에 저장된 상품 번호를 조회한다.")
    @Test
    void findLatestProductNumber() {
        var latestProductNumber = productRepository.findLatestProductNumber();

        assertThat(latestProductNumber).isEqualTo(LATEST_PRODUCT_NUMBER);
    }

    @DisplayName("상품이 하나도 없는 경우에는 null을 반환한다.")
    @Test
    void findLatestProductNumberWhenProductIsEmpty() {
        productRepository.deleteAllInBatch();
        var latestProductNumber = productRepository.findLatestProductNumber();

        assertThat(latestProductNumber).isNull();
    }
}