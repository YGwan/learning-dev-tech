package org.example.domain.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.domain.constant.ProductType.*;

class ProductTypeTest {

    @DisplayName("HANDMADE 타입의 상품일 경우 재고가 관리될 필요가 없는 상품으로 판별한다.")
    @Test
    void containsStockTypeWhenHandMadeTypeProduct() {
        var handmadeType = HANDMADE;

        var result = containsStockType(handmadeType);

        assertThat(result).isFalse();
    }

    @DisplayName("BAKERY 타입의 상품일 경우 재고가 관리될 필요가 없는 상품으로 판별한다.")
    @Test
    void containsStockTypeWhenBakeryTypeProduct() {
        var bakeryType = BAKERY;

        var result = containsStockType(bakeryType);

        assertThat(result).isTrue();
    }

    @DisplayName("HANDMADE 타입의 상품일 경우 재고가 관리될 필요가 없는 상품으로 판별한다.")
    @Test
    void containsStockTypeWhenBottleTypeProduct() {
        var bottleType = BOTTLE;

        var result = containsStockType(bottleType);

        assertThat(result).isTrue();
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    @DisplayName("한번에 ProductType에 관한 테스트를 진행한다.")
    class ProductTypeAllTest {

        @DisplayName("재고 관리를 해야하는 상품 타입일 경우 true를 아니면 false를 반환한다. - 나열")
        @Test
        void containsStockTypeAll1() {
            ProductType handmadeType = HANDMADE;
            ProductType bakeryType = BAKERY;
            ProductType bottleType = BOTTLE;

            boolean handmadeTypeResult = containsStockType(handmadeType);
            boolean bakeryTypeResult = containsStockType(bakeryType);
            boolean bottleTypeResult = containsStockType(bottleType);

            assertThat(handmadeTypeResult).isFalse();
            assertThat(bakeryTypeResult).isTrue();
            assertThat(bottleTypeResult).isTrue();
        }

        @DisplayName("재고 관리를 해야하는 상품 타입일 경우 true를 아니면 false를 반환한다. - @ParameterizedTest : CsvSource")
        @CsvSource({"HANDMADE,false", "BOTTLE,true", "BAKERY, true"})
        @ParameterizedTest
        void containsStockTypeAll2(ProductType productType, boolean expected) {
            boolean result = containsStockType(productType);

            assertThat(result).isEqualTo(expected);
        }

        @DisplayName("재고 관리를 해야하는 상품 타입일 경우 true를 아니면 false를 반환한다. - @ParameterizedTest : methodSource")
        @MethodSource("provideProductTypeForCheckingStockType")
        @ParameterizedTest
        void containsStockTypeAll3(ProductType productType, boolean expected) {
            boolean result = containsStockType(productType);

            assertThat(result).isEqualTo(expected);
        }

        private Stream<Arguments> provideProductTypeForCheckingStockType() {
            return Stream.of(
                    Arguments.of(HANDMADE, false),
                    Arguments.of(BAKERY, true),
                    Arguments.of(BOTTLE, true)
            );
        }
    }
}
