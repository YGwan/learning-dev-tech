package org.example.domain;

import org.example.domain.constant.OrderStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.domain.constant.ProductStatus.*;
import static org.example.domain.constant.ProductType.HANDMADE;

class OrderTest {

    private static final List<Product> products = new ArrayList<>();

    @BeforeAll
    public static void init() {
        var product1 = Product.builder().productNumber("001").productType(HANDMADE).productStatus(SELLING).name("아메리카노").price(4000).build();

        var product2 = Product.builder().productNumber("002").productType(HANDMADE).productStatus(HOLD).name("카페라떼").price(4500).build();

        var product3 = Product.builder().productNumber("003").productType(HANDMADE).productStatus(STOP).name("팥빙수").price(7000).build();

        products.add(product1);
        products.add(product2);
        products.add(product3);

    }

    @DisplayName("주문 생성 시 상품 리스트에 따른 주문의 총 금액을 계산한다.")
    @Test
    void calculateTotalPriceWhenCreatingOrder() {
        var order = Order.create(products, LocalDateTime.now());

        assertThat(order.getTotalPrice()).isEqualTo(15500);
    }

    @DisplayName("주문 생성 시 주문 상태는 INIT인지 확인한다.")
    @Test
    void orderStatusInitWhenCreatingOrder() {
        var order = Order.create(products, LocalDateTime.now());

        assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
    }

    @DisplayName("주문 생성 시 등록 시간을 확인한다.")
    @Test
    void registeredTimeWhenCreatingOrder() {
        var registeredDateTime = LocalDateTime.now();
        var order = Order.create(products, registeredDateTime);

        assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
    }
}
