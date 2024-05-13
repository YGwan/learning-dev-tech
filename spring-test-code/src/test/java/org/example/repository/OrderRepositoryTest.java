package org.example.repository;

import org.example.domain.Order;
import org.example.domain.Product;
import org.example.domain.constant.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.example.domain.constant.ProductStatus.*;
import static org.example.domain.constant.ProductType.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    private List<Product> products = new ArrayList<>();

    private int totalPrice;

    @BeforeEach
    void init() {
        var product1 = Product.builder()
                .productNumber("001")
                .productType(BOTTLE)
                .productStatus(SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        var product2 = Product.builder()
                .productNumber("002")
                .productType(BAKERY)
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

        var product4 = Product.builder()
                .productNumber("004")
                .productType(HANDMADE)
                .productStatus(STOP)
                .name("아이스크림")
                .price(5000)
                .build();

        products = List.of(product1, product2, product3, product4);

        totalPrice = products.stream()
                .mapToInt(Product::getPrice)
                .sum();

        productRepository.saveAll(products);
    }

    @DisplayName("완료된 주문 건 중, 주문 일시 범위에 해당하는 주문 목록을 조회한다.")
    @Test
    void findOrdersBy() {
        var orderDate = LocalDate.now();

        var order1 = Order.create(products, LocalDateTime.now());
        var order2 = Order.create(products, LocalDateTime.now());
        order1.modifyOrderStatus(OrderStatus.PAYMENT_COMPLETED);

        orderRepository.saveAll(List.of(order1, order2));

        var startDateTime = orderDate.atStartOfDay();
        var endDateTime = orderDate.plusDays(1).atStartOfDay();
        var paymentCompletedOrders = orderRepository.findOrdersBy(startDateTime, endDateTime, OrderStatus.PAYMENT_COMPLETED);

        assertThat(paymentCompletedOrders).hasSize(1)
                .extracting("totalPrice", "orderStatus")
                .containsExactlyInAnyOrder(
                        tuple(totalPrice, OrderStatus.PAYMENT_COMPLETED)
                );
    }
}