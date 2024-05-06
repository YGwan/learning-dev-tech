package org.example.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StockTest {

    @DisplayName("재고의 수량이 주문 수량보다 작을 경우 false를 반환한다.")
    @Test
    void isQuantityEnough() {
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        var result = stock.isQuantityEnough(quantity);

        assertThat(result).isFalse();
    }

    @DisplayName("재고의 수량이 주문 수량보다 많을 경우 재고 수량을 주문 수량만큼 뺀 값이 재고 수량으로 남는지 확인한다.")
    @Test
    void deductQuantity() {
        int stockQuantity = 2;
        int quantity = 1;

        Stock stock = Stock.create("001", stockQuantity);
        stock.deductQuantity(quantity);

        assertThat(stock.getQuantity()).isEqualTo(stockQuantity - quantity);
    }

    @DisplayName("재고의 수량이 주문 수량보다 적을 경우 에러를 발생시킨다.")
    @Test
    void deductQuantity2() {
        int stockQuantity = 1;
        int quantity = 2;

        Stock stock = Stock.create("001", stockQuantity);

        assertThatThrownBy(() -> stock.deductQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고 수량이 부족합니다.");
    }
}