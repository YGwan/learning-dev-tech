package org.example.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ProductType {

    HANDMADE("제조 음료"),
    BOTTLE("병 음료"),
    BAKERY("베이커리");

    private final String text;
    private static final List<ProductType> STOCK_TYPE = List.of(BOTTLE, BAKERY);

    public static boolean containsStockType(ProductType type) {
        return STOCK_TYPE.contains(type);
    }
}
