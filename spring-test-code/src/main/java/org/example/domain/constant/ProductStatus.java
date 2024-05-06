package org.example.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {

    SELLING("판매중"),
    HOLD("판매 보류"),
    STOP("판매 중지");

    private final String text;

    public static List<ProductStatus> sellingStatus() {
        return List.of(SELLING, HOLD);
    }
}
