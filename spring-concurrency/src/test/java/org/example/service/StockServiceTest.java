package org.example.service;

import org.example.domain.Stock;
import org.example.repository.StockRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;


    @BeforeEach
    public void before() {
        stockRepository.save(new Stock(1L, 100L));
    }

    @AfterEach
    public void after() {
        stockRepository.deleteAll();
    }

    @DisplayName("재고 감소")
    @Test
    public void decreaseQuantityTest() {
        stockService.decrease(1L, 1L);

        Stock stock = stockService.findById(1L);
        Assertions.assertEquals(99, stock.getQuantity());
    }
}