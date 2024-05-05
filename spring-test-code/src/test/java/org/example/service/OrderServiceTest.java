package org.example.service;

import org.example.domain.Product;
import org.example.dto.request.OrderCreateRequest;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.example.domain.constant.ProductStatus.*;
import static org.example.domain.constant.ProductType.HANDMADE;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderService orderService;

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
                .productNumber("003")
                .productType(HANDMADE)
                .productStatus(STOP)
                .name("팥빙수")
                .price(7000)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));
    }

    @DisplayName("주문번호 리스트를 받고 주문을 생성한다.")
    @Test
    void createOrderTest() {
        var request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "002"))
                .build();
        var registeredDateTime = LocalDateTime.now();
        var response = orderService.createOrder(request, registeredDateTime);

        assertThat(response.getId()).isNotNull();

        assertThat(response)
                .extracting("registeredDateTime", "totalPrice")
                .contains(registeredDateTime, 8500);

        assertThat(response.getProducts()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 4000),
                        tuple("002", 4500)
                );
    }

}