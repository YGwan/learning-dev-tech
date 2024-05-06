package org.example.domain.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeTest {

    @DisplayName("상품 타입이 재고가 관리되어야 되는 타입인지를 확인한다.")
    @Test
    void containsStockType() {
        ProductType handmadeType = ProductType.HANDMADE;
        ProductType bakeryType = ProductType.BAKERY;
        ProductType bottleType = ProductType.BOTTLE;

        boolean handmadeTypeResult = ProductType.containsStockType(handmadeType);
        boolean bakeryTypeResult = ProductType.containsStockType(bakeryType);
        boolean bottleTypeResult = ProductType.containsStockType(bottleType);

        assertThat(handmadeTypeResult).isFalse();
        assertThat(bakeryTypeResult).isTrue();
        assertThat(bottleTypeResult).isTrue();
    }
}
