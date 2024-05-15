package org.example.domain;

import org.example.domain.constant.OrderStatus;
import org.example.domain.constant.ProductStatus;
import org.example.domain.constant.ProductType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.domain.constant.ProductStatus.*;
import static org.example.domain.constant.ProductType.*;

class OrderTest {

    private static final List<Product> PRODUCTS = new ArrayList<>();
    private static final LocalDateTime REGISTERED_DATE_TIME = LocalDateTime.of(2024, 5, 15, 10, 30);

    @BeforeAll
    public static void init() {
        var product1 = createProduct("001", BAKERY, SELLING, "아메리카노", 4000);
        var product2 = createProduct("002", BOTTLE, HOLD, "카페라떼", 4500);
        var product3 = createProduct("003", HANDMADE, STOP, "팥빙수", 7000);

        PRODUCTS.add(product1);
        PRODUCTS.add(product2);
        PRODUCTS.add(product3);
    }

    @DisplayName("주문 생성 시 상품 리스트에 따른 주문의 총 금액을 계산한다.")
    @Test
    void calculateTotalPriceWhenCreatingOrder() {
        var order = Order.create(PRODUCTS, REGISTERED_DATE_TIME);

        assertThat(order.getTotalPrice()).isEqualTo(15500);
    }

    @DisplayName("주문 생성 시 주문 상태는 INIT인지 확인한다.")
    @Test
    void orderStatusInitWhenCreatingOrder() {
        var order = Order.create(PRODUCTS, REGISTERED_DATE_TIME);

        assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
    }

    @DisplayName("주문 생성 시 등록 시간을 확인한다.")
    @Test
    void registeredTimeWhenCreatingOrder() {
        var order = Order.create(PRODUCTS, REGISTERED_DATE_TIME);

        assertThat(order.getRegisteredDateTime()).isEqualTo(REGISTERED_DATE_TIME);
    }

    private static Product createProduct(String productNumber, ProductType productType, ProductStatus status, String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .productType(productType)
                .productStatus(status)
                .name(name)
                .price(price)
                .build();
    }
}
