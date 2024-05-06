package org.example.repository;

import org.example.domain.Stock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@DataJpaTest
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @DisplayName("상품 번호 리스트로ㄷ 따른 재고를 조회한다,")
    @Test
    void test() {
        var stock1 = Stock.create("001", 2);
        var stock2 = Stock.create("002", 4);

        stockRepository.saveAll(List.of(stock1, stock2));

        var stocks = stockRepository.findAllByProductNumberIn(List.of("001", "002"));

        assertThat(stocks).hasSize(2)
                .extracting("productNumber", "quantity")
                .containsExactlyInAnyOrder(
                        tuple("001", 2),
                        tuple("002", 4)
                );
    }
}
