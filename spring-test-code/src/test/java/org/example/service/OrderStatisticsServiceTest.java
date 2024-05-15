package org.example.service;

import org.example.domain.MailSendHistory;
import org.example.domain.Order;
import org.example.domain.Product;
import org.example.domain.constant.OrderStatus;
import org.example.domain.constant.ProductStatus;
import org.example.domain.constant.ProductType;
import org.example.repository.MailSendHistoryRepository;
import org.example.repository.OrderProductRepository;
import org.example.repository.OrderRepository;
import org.example.repository.ProductRepository;
import org.example.service.mail.MailSendClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.domain.constant.ProductStatus.*;
import static org.example.domain.constant.ProductType.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderStatisticsServiceTest {

    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    @MockBean
    private MailSendClient mailSendClient;

    private final List<Product> products = new ArrayList<>();

    @BeforeEach
    void init() {
        var product1 = createProduct("001", BAKERY, SELLING, "아메리카노", 4000);
        var product2 = createProduct("002", BOTTLE, HOLD, "카페라떼", 4500);
        var product3 = createProduct("003", HANDMADE, STOP, "팥빙수", 7000);
        var product4 = createProduct("004", HANDMADE, STOP, "아이스크림", 5000);

        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);

        productRepository.saveAll(products);
    }

    @AfterEach
    void end() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
    @Test
    void sendOrderStatisticsMail() {
        var orderDate = LocalDate.of(2024, 5, 15);
        var registeredDateTime = orderDate.atTime(10, 30);

        var product1 = createProduct("001", BAKERY, SELLING, "아메리카노", 4000);
        var product2 = createProduct("002", BOTTLE, HOLD, "카페라떼", 4500);
        var product3 = createProduct("003", HANDMADE, STOP, "팥빙수", 7000);
        var products = List.of(product1, product2, product3);
        productRepository.saveAll(products);

        var order1 = createPaymentCompletedOrder(products, registeredDateTime.with(LocalDateTime.MAX));
        var order2 = createPaymentCompletedOrder(products, registeredDateTime);
        var order3 = createPaymentCompletedOrder(products, registeredDateTime.minusDays(1));
        var order4 = createPaymentCompletedOrder(products, registeredDateTime.plusDays(1));
        orderRepository.saveAll(List.of(order1, order2, order3, order4));

        // stubbing ( mock 객체에 원하는 행위를 강제한다. )
        when(mailSendClient.sendMail(any(String.class), any(String.class), any(String.class), any(String.class)))
                .thenReturn(true);

        boolean result = orderStatisticsService.sendOrderStatisticsMail(orderDate, "test@test.com");

        assertThat(result).isTrue();

        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1)
                .extracting("content")
                .contains("총 매출 합계 : 15500");
    }

    private Order createPaymentCompletedOrder(List<Product> products, LocalDateTime now) {
        var order = Order.create(products, now);
        order.modifyOrderStatus(OrderStatus.PAYMENT_COMPLETED);
        return order;
    }

    private Product createProduct(String productNumber, ProductType productType, ProductStatus status, String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .productType(productType)
                .productStatus(status)
                .name(name)
                .price(price)
                .build();
    }
}
