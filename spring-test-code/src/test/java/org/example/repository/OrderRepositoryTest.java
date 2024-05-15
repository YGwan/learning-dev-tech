package org.example.repository;

import org.example.domain.Order;
import org.example.domain.Product;
import org.example.domain.constant.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.example.domain.constant.ProductStatus.*;
import static org.example.domain.constant.ProductType.*;
import static org.example.utils.ProductFixture.createProduct;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    private List<Product> products = new ArrayList<>();

    private int totalPrice;

    @DisplayName("완료된 주문 건 중, 주문 일시 범위에 해당하는 주문 목록을 조회한다.")
    @Test
    void findOrdersBy() {
        setProducts();

        var orderDate = LocalDate.of(2024, 5, 15);
        var registeredDateTime = orderDate.atTime(10, 30);

        var order1 = Order.create(products, registeredDateTime);
        var order2 = Order.create(products, registeredDateTime);
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

    private void setProducts() {
        var product1 = createProduct("001", BAKERY, SELLING, "아메리카노", 4000);
        var product2 = createProduct("002", BOTTLE, HOLD, "카페라떼", 4500);
        var product3 = createProduct("003", HANDMADE, STOP, "팥빙수", 7000);

        products = List.of(product1, product2, product3);

        totalPrice = products.stream()
                .mapToInt(Product::getPrice)
                .sum();

        productRepository.saveAll(products);
    }
}
