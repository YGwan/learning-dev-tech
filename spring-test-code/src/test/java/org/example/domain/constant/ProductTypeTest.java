package org.example.domain.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeTest {

    @DisplayName("HANDMADE 타입의 상품일 경우 재고가 관리될 필요가 없는 상품으로 판별한다.")
    @Test
    void containsStockTypeWhenHandMadeTypeProduct() {
        ProductType handmadeType = ProductType.HANDMADE;

        var result = ProductType.containsStockType(handmadeType);

        assertThat(result).isFalse();
    }

    @DisplayName("BAKERY 타입의 상품일 경우 재고가 관리될 필요가 없는 상품으로 판별한다.")
    @Test
    void containsStockTypeWhenBakeryTypeProduct() {
        ProductType bakeryType = ProductType.BAKERY;

        var result = ProductType.containsStockType(bakeryType);

        assertThat(result).isTrue();
    }

    @DisplayName("HANDMADE 타입의 상품일 경우 재고가 관리될 필요가 없는 상품으로 판별한다.")
    @Test
    void containsStockTypeWhenBottleTypeProduct() {
        ProductType bottleType = ProductType.BOTTLE;

        var result = ProductType.containsStockType(bottleType);

        assertThat(result).isTrue();
    }
}
