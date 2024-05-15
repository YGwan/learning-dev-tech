package org.example.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.example.domain.constant.ProductStatus.*;
import static org.example.domain.constant.ProductType.*;
import static org.example.utils.ProductFixture.createProduct;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("판매상태에 따른 상품들을 조회한다.")
    @Test
    void findAllByProductStatus() {
        var product1 = createProduct("001", BAKERY, SELLING, "아메리카노", 4000);
        var product2 = createProduct("002", BOTTLE, HOLD, "카페라떼", 4500);
        var product3 = createProduct("003", HANDMADE, STOP, "팥빙수", 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

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
        var product1 = createProduct("001", BAKERY, SELLING, "아메리카노", 4000);
        var product2 = createProduct("002", BOTTLE, HOLD, "카페라떼", 4500);
        var product3 = createProduct("003", HANDMADE, STOP, "팥빙수", 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

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
        var lastProductNumber = "003";

        var product1 = createProduct("001", BAKERY, SELLING, "아메리카노", 4000);
        var product2 = createProduct("002", BOTTLE, HOLD, "카페라떼", 4500);
        var product3 = createProduct(lastProductNumber, HANDMADE, STOP, "팥빙수", 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        var latestProductNumber = productRepository.findLatestProductNumber();

        assertThat(latestProductNumber).isEqualTo(lastProductNumber);
    }

    @DisplayName("상품이 하나도 없는 경우에는 null을 반환한다.")
    @Test
    void findLatestProductNumberWhenProductIsEmpty() {
        var latestProductNumber = productRepository.findLatestProductNumber();

        assertThat(latestProductNumber).isNull();
    }
}
