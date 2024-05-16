package org.example.service;

import org.example.domain.Stock;
import org.example.dto.request.OrderCreateRequest;
import org.example.integration.ServiceTestContext;
import org.example.repository.OrderProductRepository;
import org.example.repository.OrderRepository;
import org.example.repository.ProductRepository;
import org.example.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.example.domain.constant.ProductStatus.*;
import static org.example.domain.constant.ProductType.*;
import static org.example.utils.ProductFixture.createProduct;

//@Transactional
class OrderServiceTest extends ServiceTestContext {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private OrderService orderService;

    private static final LocalDateTime REGISTERED_DATE_TIME = LocalDateTime.of(2024, 5, 15, 10, 30);

    @BeforeEach
    void init() {
        var product1 = createProduct("001", BAKERY, SELLING, "아메리카노", 4000);
        var product2 = createProduct("002", BOTTLE, HOLD, "카페라떼", 4500);
        var product3 = createProduct("003", HANDMADE, STOP, "팥빙수", 7000);
        var product4 = createProduct("004", HANDMADE, STOP, "아이스크림", 5000);
        productRepository.saveAll(List.of(product1, product2, product3, product4));
    }

    @AfterEach
    void end() {
        orderProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();
    }

    @DisplayName("주문번호 리스트를 받고 주문을 생성한다.")
    @Test
    void createOrder() {
        var request = OrderCreateRequest.builder()
                .productNumbers(List.of("003", "004"))
                .build();

        var response = orderService.createOrder(request, REGISTERED_DATE_TIME);

        assertThat(response.getId()).isNotNull();

        assertThat(response)
                .extracting("registeredDateTime", "totalPrice")
                .contains(REGISTERED_DATE_TIME, 12000);

        assertThat(response.getProducts()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("003", 7000),
                        tuple("004", 5000)
                );
    }

    @DisplayName("중복되는 판매 상품 번호를 주문번호 리스트를 받고 주문을 생성한다.")
    @Test
    void createOrderWithDuplicatedProductNumber() {
        var request = OrderCreateRequest.builder()
                .productNumbers(List.of("003", "003"))
                .build();

        var response = orderService.createOrder(request, REGISTERED_DATE_TIME);

        assertThat(response.getId()).isNotNull();

        assertThat(response)
                .extracting("registeredDateTime", "totalPrice")
                .contains(REGISTERED_DATE_TIME, 14000);

        assertThat(response.getProducts()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("003", 7000),
                        tuple("003", 7000)
                );
    }

    @DisplayName("주문 시 재고의 양이 주문에 해당하는 갯수만큼 줄어드는지 확인한다")
    @Test
    void createOrderWithStock() {
        var stock1 = Stock.create("001", 4);
        var stock2 = Stock.create("002", 2);

        stockRepository.saveAll(List.of(stock1, stock2));

        var request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001", "002"))
                .build();

        var response = orderService.createOrder(request, REGISTERED_DATE_TIME);

        assertThat(response.getTotalPrice()).isEqualTo(12500);
        assertThat(response.getProducts()).hasSize(3);

        List<Stock> stocks = stockRepository.findAll();

        assertThat(stocks).hasSize(2)
                .extracting("productNumber", "quantity")
                .containsExactlyInAnyOrder(
                        tuple("001", 2),
                        tuple("002", 1)
                );
    }

    @DisplayName("주문 시 재고가 부족할 경우 에러를 발생시키는지 확인한다.")
    @Test
    void createOrderWithNoStock() {
        var stock1 = Stock.create("001", 1);
        var stock2 = Stock.create("002", 2);

        stockRepository.saveAll(List.of(stock1, stock2));

        var request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001", "002"))
                .build();

        assertThatThrownBy(() -> orderService.createOrder(request, REGISTERED_DATE_TIME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 충분하지 않은 상품이 존재합니다.");
    }
}
